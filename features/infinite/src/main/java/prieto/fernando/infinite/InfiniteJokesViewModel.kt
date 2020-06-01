package prieto.fernando.infinite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.model.RandomJokeDomainToUiModelMapper
import prieto.fernando.model.RandomJokeUiModel
import prieto.fernando.usecase.GetMultipleRandomJokeUseCase
import prieto.fernando.vm.BaseViewModel
import prieto.fernando.vm.BaseViewModelInputs
import prieto.fernando.vm.BaseViewModelOutputs
import timber.log.Timber
import javax.inject.Inject

interface InfiniteJokeViewModelInputs : BaseViewModelInputs {
    fun multipleRandomJokes()
    fun onJokeSelected(joke: String)
}

private const val JOKES_REQUESTED = 12

class InfiniteJokesViewModel @Inject constructor(
    private val multipleRandomJokeUseCase: GetMultipleRandomJokeUseCase,
    private val randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper
) : BaseViewModel(), InfiniteJokeViewModelInputs {

    override val inputs: InfiniteJokeViewModelInputs
        get() = this

    private val multipleRandomJokesRetrieved: MutableLiveData<List<RandomJokeUiModel>> = MutableLiveData()
    private val errorResource: MutableLiveData<Int> = MutableLiveData()
    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    private val jokeSelected: MutableLiveData<String> = MutableLiveData()

    fun multipleRandomJokesRetrieved(): LiveData<List<RandomJokeUiModel>> =
        multipleRandomJokesRetrieved

    fun errorResource(): LiveData<Int> = errorResource
    fun loading(): LiveData<Boolean> = loading
    fun jokeSelected(): LiveData<String> = jokeSelected

    override fun multipleRandomJokes() {
        multipleRandomJokeUseCase.execute(JOKES_REQUESTED)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .doOnSubscribe { loading.postValue(true) }
            .doFinally { loading.postValue(false) }
            .subscribe({ randomJokeDomainModels ->
                val randomJokeUiModels = getRandomJokeUiModels(randomJokeDomainModels)
                multipleRandomJokesRetrieved.postValue(randomJokeUiModels)
            }, { throwable ->
                Timber.d(throwable)
                errorResource.value = R.string.custom_joke_retrieving_error_generic
            }).also { subscriptions.add(it) }
    }

    override fun onJokeSelected(joke: String) {
        jokeSelected.postValue(joke)
    }

    private fun getRandomJokeUiModels(randomJokeDomainModels: List<RandomJokeDomainModel>) =
        randomJokeDomainModels.map { randomJokeDomainModel ->
            randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)
        }
}
