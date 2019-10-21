package prieto.fernando.jokesapp.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import jokesapp.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.button_custom_joke as buttonCustomJoke
import kotlinx.android.synthetic.main.fragment_dashboard.button_multiple_jokes as buttonMultipleJoke
import kotlinx.android.synthetic.main.fragment_dashboard.button_random_joke as buttonRandomJoke
import prieto.fernando.jokesapp.R
import prieto.fernando.ui.BaseFragment

class DashboardFragment : BaseFragment<DashboardViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_dashboard, container, false)!!

    override fun onResume() {
        super.onResume()
        setupInputListeners()
        setupOutputListeners()
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

    private fun setupOutputListeners() {
        viewModel.outputs.error()
            .subscribe {
            }.also { subscriptions.add(it) }

        viewModel.outputs.randomJokeRetrieved()
            .subscribe { randomJokeAndTitle ->
                showDialog(
                    randomJokeAndTitle.titleResource,
                    randomJokeAndTitle.randomJokeUiModel.joke
                )
            }.also { subscriptions.add(it) }

        viewModel.outputs.navigateToCustomJoke()
            .subscribe {
                navigateToCustomJokeFragment()
            }.also { subscriptions.add(it) }

        viewModel.outputs.navigateToInfiniteJokes()
            .subscribe {
                navigateToInfiniteJokesFragment()
            }.also { subscriptions.add(it) }

        viewModel.outputs.customRandomJokeRetrieved()
            .subscribe { customRandomJoke ->
                viewModel.inputs.resetCustomJokeCache()
                showDialog(
                    customRandomJoke.titleResource,
                    customRandomJoke.randomJokeUiModel.joke
                )
            }.also { subscriptions.add(it) }
    }

    private fun navigateToCustomJokeFragment() {
        findNavController().navigate(R.id.goToCustomJokeFragment)
    }

    private fun navigateToInfiniteJokesFragment() {
        findNavController().navigate(R.id.goToInfiniteJokesFragment)
    }

    override val viewModel: DashboardViewModel by lazy {
        ViewModelProviders.of(this, vmFactory).get(DashboardViewModel::class.java)
    }
}
