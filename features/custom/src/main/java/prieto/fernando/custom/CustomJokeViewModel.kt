package prieto.fernando.custom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import prieto.fernando.model.NamesData
import prieto.fernando.vm.BaseViewModel
import prieto.fernando.vm.BaseViewModelInputs
import prieto.fernando.usecase.GetCustomRandomJokeUseCase
import timber.log.Timber
import javax.inject.Inject

interface CustomJokeViewModelInputs : BaseViewModelInputs {
    fun onNamesChanged(namesData: NamesData)
    fun customRandomJoke(firstName: String, lastName: String)
}

class CustomJokeViewModel @Inject constructor(
    private val customJokeUseCase: GetCustomRandomJokeUseCase,
    private val buttonStateEvaluator: NamesButtonStateEvaluator
) : BaseViewModel(),
    CustomJokeViewModelInputs {

    private val doneButtonEnabled = MutableLiveData<Boolean>()
    private val customRandomJokeRetrieved = MutableLiveData<Unit>()
    private val errorResource = MutableLiveData<Int>()

    override val inputs: CustomJokeViewModelInputs
        get() = this

    fun doneButtonEnabled(): LiveData<Boolean> = doneButtonEnabled

    fun customRandomJokeRetrieved(): LiveData<Unit> = customRandomJokeRetrieved

    fun errorResource(): LiveData<Int> = errorResource

    override fun customRandomJoke(firstName: String, lastName: String) {
        customJokeUseCase.execute(firstName, lastName)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({
                customRandomJokeRetrieved.postValue(Unit)
            }, { throwable ->
                Timber.d(throwable)
                errorResource.postValue(R.string.custom_joke_retrieving_error_generic)
            }).also { subscriptions.add(it) }
    }

    override fun onNamesChanged(namesData: NamesData) {
        val namesChanged = buttonStateEvaluator.shouldEnableButton(
            namesData.firstName,
            namesData.lastName
        )
        doneButtonEnabled.postValue(namesChanged)
    }
}
