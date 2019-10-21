package prieto.fernando.jokesapp.presentation.infinite

import android.app.Application
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.presentation.data.RandomJokeUiModel
import prieto.fernando.jokesapp.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.usecase.GetMultipleRandomJokeUseCase
import timber.log.Timber

interface CustomJokeViewModelInputs : prieto.fernando.presentation.BaseViewModelInputs {
    fun multipleRandomJokes()
}

interface CustomJokeViewModelOutputs : prieto.fernando.presentation.BaseViewModelOutputs {
    fun multipleRandomJokesRetrieved(): Observable<List<RandomJokeUiModel>>
}

private const val JOKES_REQUESTED = 12

class InfiniteJokesViewModel @Inject constructor(
    application: Application,
    private val multipleRandomJokeUseCase: GetMultipleRandomJokeUseCase,
    private val randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper
) : prieto.fernando.presentation.BaseViewModel(application),
    CustomJokeViewModelInputs,
    CustomJokeViewModelOutputs {

    override val inputs: CustomJokeViewModelInputs
        get() = this

    override val outputs: CustomJokeViewModelOutputs
        get() = this

    private val multipleRandomJokesRetrieved = PublishSubject.create<List<RandomJokeUiModel>>()

    override fun multipleRandomJokes() {
        multipleRandomJokeUseCase.execute(JOKES_REQUESTED)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({ randomJokeDomainModels ->
                val randomJokeUiModels = getRandomJokeUiModels(randomJokeDomainModels)
                multipleRandomJokesRetrieved.onNext(randomJokeUiModels)
            }, { throwable ->
                Timber.d(throwable)
                error.onNext(R.string.custom_joke_retrieving_error_generic)
            }).also { subscriptions.add(it) }
    }

    private fun getRandomJokeUiModels(randomJokeDomainModels: List<RandomJokeDomainModel>) =
        randomJokeDomainModels.map { randomJokeDomainModel ->
            randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)
        }

    override fun multipleRandomJokesRetrieved(): Observable<List<RandomJokeUiModel>> {
        return multipleRandomJokesRetrieved.observeOn(schedulerProvider.ui()).hide()
    }
}
