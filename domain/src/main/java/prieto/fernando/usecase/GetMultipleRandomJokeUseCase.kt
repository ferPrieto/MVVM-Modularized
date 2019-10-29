package prieto.fernando.usecase

import dagger.Reusable
import io.reactivex.Single
import prieto.fernando.repository.JokesRepositoryImpl
import prieto.fernando.mapper.RandomJokeLocalToDomainModelMapper
import javax.inject.Inject

@Reusable
class GetMultipleRandomJokeUseCase @Inject constructor(
    private val jokesRepository: JokesRepositoryImpl,
    private val randomJokeToDomainMapper: RandomJokeLocalToDomainModelMapper
) {
    fun execute(numberOfJokes: Int) = jokesRepository.multipleRandomJokes(numberOfJokes)
        .flatMap { randomJokeLocalModels ->
            Single.just(
                randomJokeLocalModels.map { randomJokeLocalModel ->
                    randomJokeToDomainMapper.toDomain(
                        randomJokeLocalModel
                    )
                }
            )
        }
}
