package prieto.fernando.jokesapp.presentation.custom

import android.app.Application
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import prieto.fernando.jokesapp.R
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.BaseViewModelInputs
import prieto.fernando.presentation.BaseViewModelOutputs
import prieto.fernando.usecase.GetCustomRandomJokeUseCase
import timber.log.Timber

interface CustomJokeViewModelInputs : BaseViewModelInputs {
    fun onNamesChanged(namesData: NamesData)
    fun customRandomJoke(firstName: String, lastName: String)
}

interface CustomJokeViewModelOutputs : BaseViewModelOutputs {
    fun customRandomJokeRetrieved(): Observable<Unit>
    fun doneButtonEnabled(): Observable<Boolean>
}

class CustomJokeViewModel @Inject constructor(
    application: Application,
    private val customJokeUseCase: GetCustomRandomJokeUseCase,
    private val buttonStateEvaluator: NamesButtonStateEvaluator
) : BaseViewModel(application),
    CustomJokeViewModelInputs,
    CustomJokeViewModelOutputs {

    override val inputs: CustomJokeViewModelInputs
        get() = this

    override val outputs: CustomJokeViewModelOutputs
        get() = this

    private val customRandomJokeRetrieved = PublishSubject.create<Unit>()
    private val doneButtonEnabled = BehaviorSubject.createDefault(false)

    override fun customRandomJoke(firstName: String, lastName: String) {
        customJokeUseCase.execute(firstName, lastName)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({
                customRandomJokeRetrieved.onNext(Unit)
            }, { throwable ->
                Timber.d(throwable)
                error.onNext(R.string.custom_joke_retrieving_error_generic)
            }).also { subscriptions.add(it) }
    }

    override fun onNamesChanged(namesData: NamesData) {
        val buttonState = buttonStateEvaluator.shouldEnableButton(
            namesData.firstName,
            namesData.lastName
        )
        doneButtonEnabled.onNext(buttonState)
    }

    override fun customRandomJokeRetrieved(): Observable<Unit> {
        return customRandomJokeRetrieved.observeOn(schedulerProvider.ui()).hide()
    }

    override fun doneButtonEnabled(): Observable<Boolean> {
        return doneButtonEnabled.observeOn(schedulerProvider.ui()).hide()
    }
}
