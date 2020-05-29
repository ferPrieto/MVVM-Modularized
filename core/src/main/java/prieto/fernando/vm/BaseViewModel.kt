package prieto.fernando.vm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface BaseViewModelInputs

interface BaseViewModelOutputs

open class BaseViewModel : ViewModel(),
    BaseViewModelInputs, BaseViewModelOutputs {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    protected val subscriptions = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    open val inputs: BaseViewModelInputs
        get() = this

    open val outputs: BaseViewModelOutputs
        get() = this
}
