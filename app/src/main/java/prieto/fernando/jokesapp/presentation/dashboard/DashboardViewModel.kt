package jokesapp.dashboard

import android.app.Application
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.presentation.data.RandomJokeAndTitleResource
import prieto.fernando.jokesapp.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.BaseViewModelInputs
import prieto.fernando.presentation.BaseViewModelOutputs
import prieto.fernando.repository.CustomJokesException
import prieto.fernando.usecase.GetCustomRandomJokeUseCase
import prieto.fernando.usecase.GetRandomJokeUseCase
import prieto.fernando.usecase.ResetCustomRandomJokeUseCase
import timber.log.Timber

interface DashboardViewModelInputs : BaseViewModelInputs {
    fun randomJoke()
    fun onCustomRandomJokeClicked()
    fun customRandomJokeForDialog()
    fun onMultipleJokesClicked()
    fun resetCustomJokeCache()
}

interface DashboardViewModelOutputs : BaseViewModelOutputs {
    fun randomJokeRetrieved(): Observable<RandomJokeAndTitleResource>
    fun navigateToCustomJoke(): Observable<Unit>
    fun customRandomJokeRetrieved(): Observable<RandomJokeAndTitleResource>
    fun navigateToInfiniteJokes(): Observable<Unit>
}

class DashboardViewModel @Inject constructor(
    application: Application,
    private val customRandomJokeUseCase: GetCustomRandomJokeUseCase,
    private val randomJokeUseCase: GetRandomJokeUseCase,
    private val resetCustomRandomJokeUseCase: ResetCustomRandomJokeUseCase,
    private val randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper
) : BaseViewModel(application),
    DashboardViewModelInputs,
    DashboardViewModelOutputs {

    override val inputs: DashboardViewModelInputs
        get() = this

    override val outputs: DashboardViewModelOutputs
        get() = this

    private val customRandomJokeRetrieved = PublishSubject.create<RandomJokeAndTitleResource>()
    private val randomJokeRetrieved = PublishSubject.create<RandomJokeAndTitleResource>()
    private val navigateToCustomJoke = PublishSubject.create<Unit>()
    private val multipleJokesClicked = PublishSubject.create<Unit>()

    override fun randomJoke() {
        randomJokeUseCase.execute()
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({ randomJokeDomainModel ->
                val randomJokeUiModel = randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)
                val randomJokeAndTitleResource =
                    RandomJokeAndTitleResource(
                        randomJokeUiModel,
                        R.string.dashboard_dialog_title
                    )

                randomJokeRetrieved.onNext(randomJokeAndTitleResource)
            }, { throwable ->
                Timber.d(throwable)
                error.onNext(R.string.random_joke_retrieving_error_generic)
            }).also { subscriptions.add(it) }
    }

    override fun resetCustomJokeCache() {
        resetCustomRandomJokeUseCase.execute()
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({
            }, { throwable ->
                Timber.d(throwable)
            }).also { subscriptions.add(it) }
    }

    override fun onCustomRandomJokeClicked() {
        navigateToCustomJoke.onNext(Unit)
    }

    override fun onMultipleJokesClicked() {
        multipleJokesClicked.onNext(Unit)
    }

    @Throws(CustomJokesException::class)
    override fun customRandomJokeForDialog() {
        customRandomJokeUseCase.execute(null, null)
            .compose(schedulerProvider.doOnIoObserveOnMainSingle())
            .subscribe({ randomJokeDomainModel ->
                val randomJokeUiModel = randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)
                val randomJokeAndTitleResource =
                    RandomJokeAndTitleResource(
                        randomJokeUiModel,
                        R.string.custom_joke_dialog_title
                    )
                customRandomJokeRetrieved.onNext(randomJokeAndTitleResource)
            }, { throwable ->
                Timber.d(throwable)
            }).also { subscriptions.add(it) }
    }

    override fun randomJokeRetrieved(): Observable<RandomJokeAndTitleResource> {
        return randomJokeRetrieved.observeOn(schedulerProvider.ui()).hide()
    }

    override fun navigateToCustomJoke(): Observable<Unit> {
        return navigateToCustomJoke.observeOn(schedulerProvider.ui()).hide()
    }

    override fun customRandomJokeRetrieved(): Observable<RandomJokeAndTitleResource> {
        return customRandomJokeRetrieved.observeOn(schedulerProvider.ui()).hide()
    }

    override fun navigateToInfiniteJokes(): Observable<Unit> {
        return multipleJokesClicked.observeOn(schedulerProvider.ui()).hide()
    }
}
