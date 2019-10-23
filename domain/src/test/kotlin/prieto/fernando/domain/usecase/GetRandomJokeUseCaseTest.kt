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
import prieto.fernando.usecase.GetRandomJokeUseCase

@RunWith(MockitoJUnitRunner::class)
class GetRandomJokeUseCaseTest {
    private lateinit var cut: GetRandomJokeUseCase

    @Mock
    lateinit var jokesRepository: JokesRepositoryImpl

    @Mock
    lateinit var randomJokeToDomainMapper: RandomJokeLocalToDomainModelMapper

    @Before
    fun setUp() {
        cut = GetRandomJokeUseCase(jokesRepository, randomJokeToDomainMapper)
    }

    @Test
    fun `When execute then returns expected RandomJokeLocalModel`() {
        // Given
        val randomJokeLocalModel = RandomJokeLocalModel(
            "some Id",
            "nice joke",
            listOf(CategoryLocalModel.EXPLICIT)
        )
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "nice joke",
            listOf(CategoryDomainModel.EXPLICIT)
        )
        whenever(jokesRepository.randomJoke()).thenReturn(
            Single.just(randomJokeLocalModel)
        )
        whenever(randomJokeToDomainMapper.toDomain(randomJokeLocalModel))
            .thenReturn(randomJokeDomainModel)

        // When
        val actualValue = cut.execute()

        // Then
        actualValue.test()
            .assertResult(randomJokeDomainModel)
            .assertComplete()
            .assertNoErrors()
    }
}
