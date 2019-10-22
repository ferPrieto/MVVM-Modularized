package prieto.fernando.jokesapp.presentation.infinite

import android.app.Application
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.jokesapp.presentation.data.RandomJokeUiModel
import prieto.fernando.jokesapp.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.jokesapp.presentation.setupViewModelForTests
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

    private lateinit var multipleRandomJokesRetrievedTestObserver: TestObserver<List<RandomJokeUiModel>>

    @Before
    fun setUp() {
        cut = InfiniteJokesViewModel(
            application,
            multipleRandomJokeUseCase,
            randomJokeDomainToUiModelMapper
        )
        setupViewModelForTests(cut)
        multipleRandomJokesRetrievedTestObserver = cut.outputs.multipleRandomJokesRetrieved().test()
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
        multipleRandomJokesRetrievedTestObserver.assertValue(randomJokeUiModelList)
            .assertNoErrors()
    }
}
