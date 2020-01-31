package prieto.fernando.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface BaseViewModelInputs


open class BaseViewModel : ViewModel(),
    BaseViewModelInputs {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    protected val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    open val inputs: BaseViewModelInputs
        get() = this

}
