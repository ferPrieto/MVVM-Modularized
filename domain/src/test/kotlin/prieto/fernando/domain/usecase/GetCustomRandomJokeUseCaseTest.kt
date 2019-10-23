package prieto.fernando.domain.usecase

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import java.lang.Exception
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
import prieto.fernando.usecase.GetCustomRandomJokeUseCase

@RunWith(MockitoJUnitRunner::class)
class GetCustomRandomJokeUseCaseTest {
    private lateinit var cut: GetCustomRandomJokeUseCase

    @Mock
    lateinit var jokesRepository: JokesRepositoryImpl

    @Mock
    lateinit var randomJokeToDomainMapper: RandomJokeLocalToDomainModelMapper

    @Before
    fun setUp() {
        cut = GetCustomRandomJokeUseCase(jokesRepository, randomJokeToDomainMapper)
    }

    @Test
    fun `When execute then returns expected RandomJokeLocalModel`() {
        // Given
        val randomJokeLocalModel = RandomJokeLocalModel(
            "some Id",
            "nice joke FirstName LastName",
            listOf(CategoryLocalModel.EXPLICIT)
        )
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "nice joke FirstName LastName",
            listOf(CategoryDomainModel.EXPLICIT)
        )
        whenever(jokesRepository.randomCustomJoke("FirstName", "LastName"))
            .thenReturn(Single.just(randomJokeLocalModel))

        whenever(randomJokeToDomainMapper.toDomain(randomJokeLocalModel))
            .thenReturn(randomJokeDomainModel)

        // When
        val actualValue = cut.execute("FirstName", "LastName")

        // Then
        actualValue.test()
            .assertResult(randomJokeDomainModel)
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun `Given no names When execute then returns expected RandomJokeLocalMode from cache`() {
        // Given
        val randomJokeLocalModel = RandomJokeLocalModel(
            "some Id",
            "nice joke FirstName LastName",
            listOf(CategoryLocalModel.EXPLICIT)
        )
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "nice joke FirstName LastName",
            listOf(CategoryDomainModel.EXPLICIT)
        )
        whenever(jokesRepository.randomCustomJoke(null, null))
            .thenReturn(Single.just(randomJokeLocalModel))

        whenever(randomJokeToDomainMapper.toDomain(randomJokeLocalModel))
            .thenReturn(randomJokeDomainModel)

        // When
        val actualValue = cut.execute(null, null)

        // Then
        actualValue.test()
            .assertResult(randomJokeDomainModel)
            .assertComplete()
            .assertNoErrors()
    }

    @Test(expected = Exception::class)
    fun `Given no names When execute then returns exception`() {
        // Given
        whenever(jokesRepository.randomCustomJoke(null, null))
            .thenThrow()

        // When
        cut.execute(null, null)

        // Then
        // Throws CustomJokesException
    }
}
