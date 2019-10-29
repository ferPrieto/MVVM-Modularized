package prieto.fernando.usecase

import dagger.Reusable
import prieto.fernando.repository.JokesRepositoryImpl
import prieto.fernando.mapper.RandomJokeLocalToDomainModelMapper
import javax.inject.Inject

@Reusable
class GetCustomRandomJokeUseCase @Inject constructor(
    private val jokesRepository: JokesRepositoryImpl,
    private val randomJokeToDomainMapper: RandomJokeLocalToDomainModelMapper
) {
    fun execute(firstName: String?, lastName: String?) =
        jokesRepository.randomCustomJoke(firstName, lastName)
            .map { randomJokeLocalModel ->
                randomJokeToDomainMapper.toDomain(randomJokeLocalModel)
            }
}
