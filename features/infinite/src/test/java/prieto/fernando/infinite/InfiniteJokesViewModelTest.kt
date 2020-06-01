package prieto.fernando.infinite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.model.RandomJokeDomainToUiModelMapper
import prieto.fernando.model.RandomJokeUiModel
import prieto.fernando.usecase.GetMultipleRandomJokeUseCase
import prieto.fernando.vm.TestSchedulerProvider

@RunWith(MockitoJUnitRunner::class)
class InfiniteJokesViewModelTest {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var cut: InfiniteJokesViewModel

    @Mock
    lateinit var multipleRandomJokeUseCase: GetMultipleRandomJokeUseCase

    @Mock
    lateinit var randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var multipleRandomJokesRetrievedTestObserver: Observer<List<RandomJokeUiModel>>
    private lateinit var jokeSelectedTestObserver: Observer<String>
    private lateinit var loadingTestObserver: Observer<Boolean>
    private lateinit var errorResourceTestObserver: Observer<Int>

    @Before
    fun setUp() {
        cut = InfiniteJokesViewModel(
            multipleRandomJokeUseCase,
            randomJokeDomainToUiModelMapper
        )
        cut.schedulerProvider = TestSchedulerProvider()

        multipleRandomJokesRetrievedTestObserver = mock()
        jokeSelectedTestObserver = mock()
        loadingTestObserver = mock()
        errorResourceTestObserver = mock()

        cut.multipleRandomJokesRetrieved().observeForever(multipleRandomJokesRetrievedTestObserver)
        cut.jokeSelected().observeForever(jokeSelectedTestObserver)
        cut.loading().observeForever(loadingTestObserver)
        cut.errorResource().observeForever(errorResourceTestObserver)
    }

    @Test
    fun `When multipleRandomJokes then multipleRandomJokesRetrieved invoked`() {
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
        val randomJokeUiModelList = listOf(randomJokeUiModel)
        whenever(randomJokeDomainToUiModelMapper.toUi(randomJokeDomainModel)).thenReturn(
            randomJokeUiModel
        )
        whenever(multipleRandomJokeUseCase.execute(12)).thenReturn(
            Single.just(listOf(randomJokeDomainModel))
        )

        // When
        cut.multipleRandomJokes()

        // Then
        val captor = ArgumentCaptor.forClass(RandomJokeUiModel::class.java)
        captor.run {
            verify(multipleRandomJokesRetrievedTestObserver, times(1)).onChanged(listOf(capture()))
            assertEquals(randomJokeUiModelList, value)
        }
    }
}
