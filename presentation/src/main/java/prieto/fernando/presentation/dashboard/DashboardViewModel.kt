package prieto.fernando.presentation.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import prieto.fernando.presentation.BaseViewModel
import prieto.fernando.presentation.BaseViewModelInputs
import prieto.fernando.presentation.R
import prieto.fernando.presentation.data.RandomJokeAndTitleResource
import prieto.fernando.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.repository.CustomJokesException
import prieto.fernando.usecase.GetCustomRandomJokeUseCase
import prieto.fernando.usecase.GetRandomJokeUseCase
import prieto.fernando.usecase.ResetCustomRandomJokeUseCase
import timber.log.Timber
import javax.inject.Inject

interface DashboardViewModelInputs : BaseViewModelInputs {
    fun randomJoke()
    fun onCustomRandomJokeClicked()
    fun customRandomJokeForDialog()
    fun onMultipleJokesClicked()
    fun resetCustomJokeCache()
}

class DashboardViewModel @Inject constructor(
    application: Application,
    private val customRandomJokeUseCase: GetCustomRandomJokeUseCase,
    private val randomJokeUseCase: GetRandomJokeUseCase,
    private val resetCustomRandomJokeUseCase: ResetCustomRandomJokeUseCase,
    private val randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper
) : BaseViewModel(application),
    DashboardViewModelInputs {

    private val navigateToCustomJoke: MutableLiveData<Unit> = MutableLiveData()
    private val navigateToInfiniteJokes: MutableLiveData<Unit> = MutableLiveData()
    private val randomJokeRetrieved: MutableLiveData<RandomJokeAndTitleResource> = MutableLiveData()
    private val customRandomJokeRetrieved: MutableLiveData<RandomJokeAndTitleResource> =
        MutableLiveData()
    private val errorResource: MutableLiveData<Int> = MutableLiveData()

    override val inputs: DashboardViewModelInputs
        get() = this

    fun navigateToCustomJoke(): LiveData<Unit> = navigateToCustomJoke

    fun navigateToInfiniteJokes(): LiveData<Unit> = navigateToInfiniteJokes

    fun randomJokeRetrieved(): LiveData<RandomJokeAndTitleResource> = randomJokeRetrieved

    fun customRandomJokeRetrieved(): LiveData<RandomJokeAndTitleResource> =
        customRandomJokeRetrieved

    fun errorResource(): LiveData<Int> = errorResource


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
                randomJokeRetrieved.postValue(randomJokeAndTitleResource)
            }, { throwable ->
                Timber.d(throwable)
                errorResource.value = R.string.random_joke_retrieving_error_generic
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
        navigateToCustomJoke.postValue(Unit)
    }

    override fun onMultipleJokesClicked() {
        navigateToInfiniteJokes.postValue(Unit)
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
                customRandomJokeRetrieved.postValue(randomJokeAndTitleResource)
            }, { throwable ->
                Timber.d(throwable)
            }).also { subscriptions.add(it) }
    }
}
