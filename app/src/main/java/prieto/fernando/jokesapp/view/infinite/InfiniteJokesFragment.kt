package prieto.fernando.jokesapp.view.infinite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_infinite_jokes.*
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.view.detail.DetailFragment
import prieto.fernando.jokesapp.view.extension.observe
import prieto.fernando.jokesapp.view.infinite.adapter.ClickListener
import prieto.fernando.jokesapp.view.infinite.adapter.JokesAdapter
import prieto.fernando.jokesapp.view.infinite.widget.InfiniteScrollListener
import prieto.fernando.model.RandomJokeUiModel
import prieto.fernando.presentation.infinite.InfiniteJokesViewModel
import prieto.fernando.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_infinite_jokes.infinite_jokes_recycler as infiniteRecyclerView

class InfiniteJokesFragment : BaseFragment<InfiniteJokesViewModel>(), ClickListener {

    private var jokesAdapter: JokesAdapter? = null

    override fun onItemClicked(joke: String) {
        viewModel.onJokeSelected(joke)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_infinite_jokes, container, false)!!

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        jokesAdapter = JokesAdapter(this)
        infiniteRecyclerView.adapter = jokesAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        infiniteRecyclerView.layoutManager = linearLayoutManager
        val endlessScrollListener = object : InfiniteScrollListener(linearLayoutManager) {
            override fun onLoadMore() {
                isLoading = true
                viewModel.multipleRandomJokes()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        }
        infiniteRecyclerView.addOnScrollListener(endlessScrollListener)
        
    }

    override fun onResume() {
        super.onResume()
        viewModel.inputs.multipleRandomJokes()
    }

    override val viewModel: InfiniteJokesViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(InfiniteJokesViewModel::class.java).apply {
            observe(multipleRandomJokesRetrieved(), ::addJokesToAdapter)
            observe(errorResource(), ::showErrorToast)
            observe(loading(), ::showLoading)
            observe(jokeSelected(), ::navigateToDetailFragment)
        }
    }

    private fun addJokesToAdapter(randomJokes: List<prieto.fernando.model.RandomJokeUiModel>?) {
        randomJokes?.let {
            isLoading = false
            jokesAdapter?.let { adapter ->
                adapter.setData(randomJokes)
                infiniteRecyclerView.adapter = adapter
            }
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        isLoading?.let {
            progressBar.visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun navigateToDetailFragment(joke: String?) {
        val args = Bundle().apply {
            putString(DetailFragment.DETAIL_FRAGMENT_TYPE_ARG, joke)
        }
        findNavController().navigate(R.id.goToDetailFragment, args)
    }
}
