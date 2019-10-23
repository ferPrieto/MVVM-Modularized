package prieto.fernando.usecase

import dagger.Reusable
import prieto.fernando.repository.JokesRepositoryImpl
import javax.inject.Inject

@Reusable
class ResetCustomRandomJokeUseCase @Inject constructor(
    private val jokesRepository: JokesRepositoryImpl
) {
    fun execute() = jokesRepository.resetCustomRandomJoke()
}
