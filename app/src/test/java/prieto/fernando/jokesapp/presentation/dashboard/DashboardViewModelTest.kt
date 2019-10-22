package prieto.fernando.jokesapp.presentation.dashboard

import android.app.Application
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import jokesapp.dashboard.DashboardViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.presentation.data.RandomJokeAndTitleResource
import prieto.fernando.jokesapp.presentation.data.RandomJokeUiModel
import prieto.fernando.jokesapp.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.jokesapp.presentation.setupViewModelForTests
import prieto.fernando.usecase.GetCustomRandomJokeUseCase
import prieto.fernando.usecase.GetRandomJokeUseCase
import prieto.fernando.usecase.ResetCustomRandomJokeUseCase

@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest {
    private lateinit var cut: DashboardViewModel

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var customRandomJokeUseCase: GetCustomRandomJokeUseCase

    @Mock
    lateinit var randomJokeUseCase: GetRandomJokeUseCase

    @Mock
    lateinit var resetCustomRandomJokeUseCase: ResetCustomRandomJokeUseCase

    @Mock
    lateinit var randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper

    private lateinit var randomJokeRetrievedTestObserver: TestObserver<RandomJokeAndTitleResource>
    private lateinit var navigateToCustomJokeTestObserver: TestObserver<Unit>
    private lateinit var multipleJokesClickedTestObserver: TestObserver<Unit>
    private lateinit var customRandomJokeRetrievedTestObserver: TestObserver<RandomJokeAndTitleResource>

    @Before
    fun setUp() {
        cut = DashboardViewModel(
            application,
            customRandomJokeUseCase,
            randomJokeUseCase,
            resetCustomRandomJokeUseCase,
            randomJokeDomainToUiModelMapper
        )
        setupViewModelForTests(cut)
        randomJokeRetrievedTestObserver = cut.outputs.randomJokeRetrieved().test()
        navigateToCustomJokeTestObserver = cut.outputs.navigateToCustomJoke().test()
        multipleJokesClickedTestObserver = cut.outputs.navigateToInfiniteJokes().test()
        customRandomJokeRetrievedTestObserver = cut.outputs.customRandomJokeRetrieved().test()
    }

    @Test
    fun `When randomJoke then randomJokeRetrieved invoked`() {
        // Given
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        val randomJokeUiModel = RandomJokeUiModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        whenever(randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)).thenReturn(
            randomJokeUiModel
        )
        val stringResource = R.string.dashboard_dialog_title
        val randomJokeAndTitleResource = RandomJokeAndTitleResource(
            randomJokeUiModel,
            stringResource
        )
        whenever(randomJokeUseCase.execute()).thenReturn(
            Single.just(randomJokeDomainModel)
        )

        // When
        cut.randomJoke()

        // Then
        randomJokeRetrievedTestObserver.assertValue(randomJokeAndTitleResource)
            .assertNoErrors()
    }

    @Test
    fun `When onCustomRandomJokeClicked then navigateToCustomJoke invoked`() {
        // When
        cut.onCustomRandomJokeClicked()

        // Then
        navigateToCustomJokeTestObserver.assertValue(Unit)
            .assertNoErrors()
    }

    @Test
    fun `When onMultipleJokesClicked then customRandomJokeRetrieved invoked`() {
        // When
        cut.onMultipleJokesClicked()

        // Then
        multipleJokesClickedTestObserver.assertValue(Unit)
            .assertNoErrors()
    }

    @Test
    fun `When customRandomJokeForDialog then customRandomJokeRetrieved invoked`() {
        // Given
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        val randomJokeUiModel = RandomJokeUiModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        whenever(randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)).thenReturn(
            randomJokeUiModel
        )
        val stringResource = R.string.custom_joke_dialog_title
        val randomJokeAndTitleResource = RandomJokeAndTitleResource(
            randomJokeUiModel,
            stringResource
        )
        whenever(customRandomJokeUseCase.execute(null, null)).thenReturn(
            Single.just(randomJokeDomainModel)
        )

        // When
        cut.customRandomJokeForDialog()

        // Then
        customRandomJokeRetrievedTestObserver.assertValue(randomJokeAndTitleResource)
            .assertNoErrors()

    }
}
