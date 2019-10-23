package prieto.fernando.jokesapp.presentation.custom

import android.app.Application
import androidx.lifecycle.MutableLiveData
import prieto.fernando.jokesapp.R
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.BaseViewModelInputs
import prieto.fernando.usecase.GetCustomRandomJokeUseCase
import timber.log.Timber
import javax.inject.Inject

interface CustomJokeViewModelInputs : BaseViewModelInputs {
    fun onNamesChanged(namesData: NamesData)
    fun customRandomJoke(firstName: String, lastName: String)
}

class CustomJokeViewModel @Inject constructor(
    application: Application,
    private val customJokeUseCase: GetCustomRandomJokeUseCase,
    private val buttonStateEvaluator: NamesButtonStateEvaluator
) : BaseViewModel(application),
    CustomJokeViewModelInputs {

    val doneButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val liveDataCustomRandomJokeRetrieved: MutableLiveData<Unit> = MutableLiveData()

    override val inputs: CustomJokeViewModelInputs
        get() = this

    override fun customRandomJoke(firstName: String, lastName: String) {
        customJokeUseCase.execute(firstName, lastName)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({
                liveDataCustomRandomJokeRetrieved.postValue(Unit)
                //customRandomJokeRetrieved.onNext(Unit)
            }, { throwable ->
                Timber.d(throwable)
                error.onNext(R.string.custom_joke_retrieving_error_generic)
            }).also { subscriptions.add(it) }
    }

    override fun onNamesChanged(namesData: NamesData) {
        doneButtonEnabled.value = buttonStateEvaluator.shouldEnableButton(
            namesData.firstName,
            namesData.lastName
        )
    }
}
