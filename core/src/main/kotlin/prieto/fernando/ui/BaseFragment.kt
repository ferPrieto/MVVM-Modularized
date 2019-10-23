package prieto.fernando.ui

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import prieto.fernando.core.R
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.SchedulerProvider
import prieto.fernando.presentation.ViewModelProviderFactory

abstract class BaseFragment<T : BaseViewModel> : DaggerFragment(), BaseView<T> {

    @Inject
    protected lateinit var vmFactory: ViewModelProviderFactory<T>

    @Inject
    lateinit var schedulers: SchedulerProvider

    protected val subscriptions = CompositeDisposable()

    protected fun showDialog(titleResource: Int, jokeContent: String) {
        AlertDialog.Builder(context)
            .setTitle(titleResource)
            .setMessage(jokeContent)
            .setPositiveButton(R.string.dashboard_dialog_dismiss_button) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    protected fun showErrorToast(errorResource: Int?) {
        errorResource?.let {
            val errorMessage = resources.getString(errorResource)
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
        }
    }

    protected fun bindClickAction(view: View, clickAction: () -> Unit) {
        RxView.clicks(view)
            .throttleFirst(BUTTON_DEBOUNCE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .subscribe { clickAction() }
            .also { subscriptions.add(it) }
    }

    override fun onPause() {
        subscriptions.clear()
        super.onPause()
    }

    protected fun setupNavigation() {
        activity?.let {
            NavigationUI.setupActionBarWithNavController(
                this.activity as AppCompatActivity,
                findNavController()
            )
        }
    }

    protected fun <T : ViewModel> getViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, vmFactory).get(viewModelClass)

    companion object {
        const val BUTTON_DEBOUNCE_TIMEOUT_MS = 500L
    }
}
