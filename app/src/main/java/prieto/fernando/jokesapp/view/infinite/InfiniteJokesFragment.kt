package prieto.fernando.jokesapp.view.infinite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_infinite_jokes.infinite_jokes_recycler as infiniteRecyclerView
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.presentation.infinite.InfiniteJokesViewModel
import prieto.fernando.jokesapp.view.infinite.adapter.JokesAdapter
import prieto.fernando.jokesapp.view.infinite.widget.EndlessRecyclerViewScrollListener
import prieto.fernando.ui.BaseFragment

class InfiniteJokesFragment : BaseFragment<InfiniteJokesViewModel>() {

    private var jokesAdapter: JokesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_infinite_jokes, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        jokesAdapter = JokesAdapter()
        infiniteRecyclerView.adapter = jokesAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        infiniteRecyclerView.layoutManager = linearLayoutManager
        val endlessScrollListener =
            object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.inputs.multipleRandomJokes()
                }
            }
        infiniteRecyclerView.addOnScrollListener(endlessScrollListener)
    }

    override fun onResume() {
        super.onResume()
        setupInputListeners()
        setupOutputListeners()
        viewModel.inputs.multipleRandomJokes()
    }

    private fun setupInputListeners() {
    }

    private fun setupOutputListeners() {
        viewModel.outputs.error()
            .subscribe {
            }.also { subscriptions.add(it) }

        viewModel.outputs.multipleRandomJokesRetrieved()
            .subscribe { randomJokes ->
                jokesAdapter?.let { adapter ->
                    adapter.setData(randomJokes)
                    infiniteRecyclerView.adapter = adapter
                }
            }.also { subscriptions.add(it) }
    }

    override val viewModel: InfiniteJokesViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(InfiniteJokesViewModel::class.java)
    }
}
