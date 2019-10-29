package prieto.fernando.domain.usecase

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import prieto.fernando.data.CategoryDomainModel
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.mapper.RandomJokeLocalToDomainModelMapper
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.model.RandomJokeLocalModel
import prieto.fernando.repository.JokesRepositoryImpl
import prieto.fernando.usecase.GetMultipleRandomJokeUseCase

@RunWith(MockitoJUnitRunner::class)
class GetMultipleRandomJokeUseCaseTest {
    private lateinit var cut: GetMultipleRandomJokeUseCase

    @Mock
    lateinit var jokesRepository: JokesRepositoryImpl

    @Mock
    lateinit var randomJokeToDomainMapper: RandomJokeLocalToDomainModelMapper

    @Before
    fun setUp() {
        cut =
            GetMultipleRandomJokeUseCase(jokesRepository, randomJokeToDomainMapper)
    }

    @Test
    fun `Given numberOfJokes when execute then return list of RandomJokeDomainModel`() {
        // Given
        val numberOfJokes = 2
        val randomJokeLocalModelFirst = RandomJokeLocalModel(
            "Id0",
            "nice joke",
            listOf(CategoryLocalModel.EXPLICIT)
        )
        val randomJokeLocalModelSecond = RandomJokeLocalModel(
            "Id1",
            "nice joke",
            listOf(CategoryLocalModel.NERDY)
        )
        val randomJokeDomainModelFirst = RandomJokeDomainModel(
            "Id0",
            "nice joke",
            listOf(CategoryDomainModel.EXPLICIT)
        )
        val randomJokeDomainModelSecond = RandomJokeDomainModel(
            "Id1",
            "nice joke",
            listOf(CategoryDomainModel.NERDY)
        )

        whenever(jokesRepository.multipleRandomJokes(numberOfJokes)).thenReturn(
            Single.just(listOf(randomJokeLocalModelFirst, randomJokeLocalModelSecond))
        )
        whenever(randomJokeToDomainMapper.toDomain(randomJokeLocalModelFirst))
            .thenReturn(randomJokeDomainModelFirst)
        whenever(randomJokeToDomainMapper.toDomain(randomJokeLocalModelSecond))
            .thenReturn(randomJokeDomainModelSecond)

        // When
        val actualValue = cut.execute(numberOfJokes)

        // Then
        actualValue.test()
            .assertResult(listOf(randomJokeDomainModelFirst, randomJokeDomainModelSecond))
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `Given 0 numberOfJokes when execute then return an empty list`() {
        // Given
        val numberOfJokes = 0

        whenever(jokesRepository.multipleRandomJokes(numberOfJokes)).thenReturn(
            Single.just(emptyList())
        )

        // When
        val actualValue = cut.execute(numberOfJokes)

        // Then
        actualValue.test()
            .assertResult(emptyList())
            .assertComplete()
            .assertNoErrors()
    }
}
