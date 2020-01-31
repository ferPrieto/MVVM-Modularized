package prieto.fernando.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import prieto.fernando.model.RandomJokeAndTitleResource
import prieto.fernando.ui.BaseFragment
import prieto.fernando.ui.observe
import kotlinx.android.synthetic.main.fragment_dashboard.button_custom_joke as buttonCustomJoke
import kotlinx.android.synthetic.main.fragment_dashboard.button_multiple_jokes as buttonMultipleJoke
import kotlinx.android.synthetic.main.fragment_dashboard.button_random_joke as buttonRandomJoke

class DashboardFragment : BaseFragment<DashboardViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_dashboard, container, false)!!

    override fun onResume() {
        super.onResume()
        setupInputListeners()
        viewModel.inputs.customRandomJokeForDialog()
    }

    private fun setupInputListeners() {
        bindClickAction(buttonRandomJoke) {
            viewModel.inputs.randomJoke()
        }
        bindClickAction(buttonCustomJoke) {
            viewModel.inputs.onCustomRandomJokeClicked()
        }
        bindClickAction(buttonMultipleJoke) {
            viewModel.inputs.onMultipleJokesClicked()
        }
    }

    override val viewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(DashboardViewModel::class.java).apply {
            observe(navigateToCustomJoke(), ::navigateToCustomJokeFragment)
            observe(navigateToInfiniteJokes(), ::navigateToInfiniteJokesFragment)
            observe(customRandomJokeRetrieved(), ::resetCacheAndShowDialog)
            observe(randomJokeRetrieved(), ::showRandomJokeDialog)
            observe(errorResource(), ::showErrorToast)
        }
    }

    private fun navigateToCustomJokeFragment(unit: Unit?) {
        findNavController().navigate(R.id.goToCustomJokeFragment)
    }

    private fun navigateToInfiniteJokesFragment(unit: Unit?) {
        findNavController().navigate(R.id.goToInfiniteJokesFragment)
    }

    private fun resetCacheAndShowDialog(customRandomJoke: RandomJokeAndTitleResource?) {
        customRandomJoke?.let {
            viewModel.inputs.resetCustomJokeCache()
            showDialog(
                customRandomJoke.titleResource,
                customRandomJoke.randomJokeUiModel.joke
            )
        }
    }

    private fun showRandomJokeDialog(randomJokeAndTitle: RandomJokeAndTitleResource?) {
        randomJokeAndTitle?.let {
            showDialog(
                randomJokeAndTitle.titleResource,
                randomJokeAndTitle.randomJokeUiModel.joke
            )
        }
    }
}
