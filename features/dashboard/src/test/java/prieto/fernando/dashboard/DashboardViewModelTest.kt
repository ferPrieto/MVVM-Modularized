package prieto.fernando.dashboard

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.model.RandomJokeAndTitleResource
import prieto.fernando.model.RandomJokeDomainToUiModelMapper
import prieto.fernando.vm.BaseSchedulerProvider
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

    @Mock
    lateinit var schedulerProvider: BaseSchedulerProvider

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var navigateToCustomJokeTestObserver: Observer<Unit>
    private lateinit var navigateToInfiniteJokesTestObserver: Observer<Unit>
    private lateinit var randomJokeRetrievedTestObserver: Observer<RandomJokeAndTitleResource>
    private lateinit var customRandomJokeRetrievedTestObserver: Observer<RandomJokeAndTitleResource>
    private lateinit var errorResourceTestObserver: Observer<Int>

    @Before
    fun setUp() {
        cut = DashboardViewModel(
            customRandomJokeUseCase,
            randomJokeUseCase,
            resetCustomRandomJokeUseCase,
            randomJokeDomainToUiModelMapper
        )
        cut.schedulerProvider = schedulerProvider

        navigateToCustomJokeTestObserver = mock()
        navigateToInfiniteJokesTestObserver = mock()
        randomJokeRetrievedTestObserver = mock()
        customRandomJokeRetrievedTestObserver = mock()
        errorResourceTestObserver = mock()
        cut.navigateToCustomJoke().observeForever(navigateToCustomJokeTestObserver)
        cut.navigateToInfiniteJokes().observeForever(navigateToInfiniteJokesTestObserver)
        cut.randomJokeRetrieved().observeForever(randomJokeRetrievedTestObserver)
        cut.customRandomJokeRetrieved().observeForever(customRandomJokeRetrievedTestObserver)
        cut.errorResource().observeForever(errorResourceTestObserver)
    }

    @Test
    fun `When randomJoke then randomJokeRetrieved invoked with expected result`() {
        // Given
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        val randomJokeUiModel = prieto.fernando.model.RandomJokeUiModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        whenever(randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)).thenReturn(
            randomJokeUiModel
        )
        val stringResource = R.string.dashboard_dialog_title
        val expected = RandomJokeAndTitleResource(
            randomJokeUiModel,
            stringResource
        )
        whenever(randomJokeUseCase.execute()).thenReturn(
            Single.just(randomJokeDomainModel)
        )
        // When
        cut.randomJoke()

        // Then
        val captor =
            ArgumentCaptor.forClass(RandomJokeAndTitleResource::class.java)
        captor.run {
            verify(randomJokeRetrievedTestObserver, times(1)).onChanged(capture())
            assertEquals(expected, value)
        }
    }

    @Test
    fun `When onCustomRandomJokeClicked then navigateToCustomJoke invoked`() {
        // When
        cut.onCustomRandomJokeClicked()

        // Then
        val captor = ArgumentCaptor.forClass(Unit::class.java)
        captor.run {
            verify(navigateToCustomJokeTestObserver, times(1)).onChanged(capture())
        }
    }

    @Test
    fun `When onMultipleJokesClicked then customRandomJokeRetrieved invoked`() {
        // When
        cut.onMultipleJokesClicked()

        // Then
        val captor = ArgumentCaptor.forClass(Unit::class.java)
        captor.run {
            verify(navigateToInfiniteJokesTestObserver, times(1)).onChanged(capture())
        }
    }

    @Test
    fun `When customRandomJokeForDialog then customRandomJokeRetrieved invoked with expected result`() {
        // Given
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        val randomJokeUiModel = prieto.fernando.model.RandomJokeUiModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )
        whenever(randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)).thenReturn(
            randomJokeUiModel
        )
        val stringResource = R.string.custom_joke_dialog_title
        val expected = RandomJokeAndTitleResource(
            randomJokeUiModel,
            stringResource
        )
        whenever(customRandomJokeUseCase.execute(null, null)).thenReturn(
            Single.just(randomJokeDomainModel)
        )

        // When
        cut.customRandomJokeForDialog()

        // Then
        val captor =
            ArgumentCaptor.forClass(RandomJokeAndTitleResource::class.java)
        captor.run {
            verify(customRandomJokeRetrievedTestObserver, times(1)).onChanged(capture())
            assertEquals(expected, value)
        }
    }
}
