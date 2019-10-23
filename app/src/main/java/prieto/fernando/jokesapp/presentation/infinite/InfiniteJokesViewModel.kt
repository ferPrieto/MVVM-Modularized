package prieto.fernando.jokesapp.presentation.infinite

import android.app.Application
import androidx.lifecycle.MutableLiveData
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.presentation.data.RandomJokeUiModel
import prieto.fernando.jokesapp.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.usecase.GetMultipleRandomJokeUseCase
import timber.log.Timber
import javax.inject.Inject

interface CustomJokeViewModelInputs : prieto.fernando.presentation.BaseViewModelInputs {
    fun multipleRandomJokes()
}

private const val JOKES_REQUESTED = 12

class InfiniteJokesViewModel @Inject constructor(
    application: Application,
    private val multipleRandomJokeUseCase: GetMultipleRandomJokeUseCase,
    private val randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper
) : BaseViewModel(application),
    CustomJokeViewModelInputs {

    override val inputs: CustomJokeViewModelInputs
        get() = this

    val multipleRandomJokesRetrieved: MutableLiveData<List<RandomJokeUiModel>> = MutableLiveData()

    override fun multipleRandomJokes() {
        multipleRandomJokeUseCase.execute(JOKES_REQUESTED)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({ randomJokeDomainModels ->
                val randomJokeUiModels = getRandomJokeUiModels(randomJokeDomainModels)
                multipleRandomJokesRetrieved.value = randomJokeUiModels
            }, { throwable ->
                Timber.d(throwable)
                error.onNext(R.string.custom_joke_retrieving_error_generic)
            }).also { subscriptions.add(it) }
    }

    private fun getRandomJokeUiModels(randomJokeDomainModels: List<RandomJokeDomainModel>) =
        randomJokeDomainModels.map { randomJokeDomainModel ->
            randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)
        }
}
