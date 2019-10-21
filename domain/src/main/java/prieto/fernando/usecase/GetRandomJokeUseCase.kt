package prieto.fernando.usecase

import dagger.Reusable
import io.reactivex.Single
import prieto.fernando.repository.JokesRepositoryImpl
import prieto.fernando.mapper.RandomJokeLocalToDomainModelMapper
import javax.inject.Inject

@Reusable
class GetRandomJokeUseCase @Inject constructor(
    private val jokesRepository: JokesRepositoryImpl,
    private val randomJokeToDomainMapper: RandomJokeLocalToDomainModelMapper
) {
    fun execute() = jokesRepository.randomJoke()
        .flatMap { randomJokeLocalModel ->
            Single.just(
                randomJokeToDomainMapper.toDomain(
                    randomJokeLocalModel
                )
            )
        }
}
