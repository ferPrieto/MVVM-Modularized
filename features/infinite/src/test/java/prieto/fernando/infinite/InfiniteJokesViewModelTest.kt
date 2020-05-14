package prieto.fernando.infinite

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
import prieto.fernando.model.RandomJokeDomainToUiModelMapper
import prieto.fernando.model.RandomJokeUiModel
import prieto.fernando.vm.BaseSchedulerProvider
import prieto.fernando.usecase.GetMultipleRandomJokeUseCase

@RunWith(MockitoJUnitRunner::class)
class InfiniteJokesViewModelTest {
    private lateinit var cut: InfiniteJokesViewModel

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var multipleRandomJokeUseCase: GetMultipleRandomJokeUseCase

    @Mock
    lateinit var randomJokeDomainToUiModelMapper: RandomJokeDomainToUiModelMapper

    @Mock
    lateinit var schedulerProvider: BaseSchedulerProvider

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var multipleRandomJokesRetrievedTestObserver: Observer<List<RandomJokeUiModel>>

    @Before
    fun setUp() {
        cut = InfiniteJokesViewModel(
            multipleRandomJokeUseCase,
            randomJokeDomainToUiModelMapper
        )
        cut.schedulerProvider = schedulerProvider

        multipleRandomJokesRetrievedTestObserver = mock()
        cut.multipleRandomJokesRetrieved().observeForever(multipleRandomJokesRetrievedTestObserver)
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
