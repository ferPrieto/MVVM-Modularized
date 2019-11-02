package prieto.fernando.presentation.infinite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.BaseViewModelInputs
import prieto.fernando.presentation.R
import prieto.fernando.presentation.RandomJokeUiModel
import prieto.fernando.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.usecase.GetMultipleRandomJokeUseCase
import timber.log.Timber
import javax.inject.Inject

interface InfiniteJokeViewModelInputs : BaseViewModelInputs {
    fun multipleRandomJokes()
}

private const val JOKES_REQUESTED = 12

class InfiniteJokesViewModel @Inject constructor(
    application: Application,
    private val multipleRandomJokeUseCase: GetMultipleRandomJokeUseCase,
    private val randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper
) : BaseViewModel(application), InfiniteJokeViewModelInputs {

    override val inputs: InfiniteJokeViewModelInputs
        get() = this

    private val multipleRandomJokesRetrieved: MutableLiveData<List<RandomJokeUiModel>> =
        MutableLiveData()
    private val errorResource: MutableLiveData<Int> = MutableLiveData()

    fun multipleRandomJokesRetrieved(): LiveData<List<RandomJokeUiModel>> =
        multipleRandomJokesRetrieved

    fun errorResource(): LiveData<Int> = errorResource

    override fun multipleRandomJokes() {
        multipleRandomJokeUseCase.execute(JOKES_REQUESTED)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({ randomJokeDomainModels ->
                val randomJokeUiModels = getRandomJokeUiModels(randomJokeDomainModels)
                multipleRandomJokesRetrieved.value = randomJokeUiModels
            }, { throwable ->
                Timber.d(throwable)
                errorResource.value = R.string.custom_joke_retrieving_error_generic
            }).also { subscriptions.add(it) }
    }

    private fun getRandomJokeUiModels(randomJokeDomainModels: List<RandomJokeDomainModel>) =
        randomJokeDomainModels.map { randomJokeDomainModel ->
            randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)
        }
}
