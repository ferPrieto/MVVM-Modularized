package prieto.fernando.data

import prieto.fernando.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokesRemoteSource
@Inject constructor(
    private val apiService: ApiService
) {
    fun getRandomJoke() = apiService.getRandomJoke()

    fun getRandomCustomJoke(firstName: String, lastName: String) =
        apiService.getRandomCustomJoke(firstName, lastName)

    fun getMultipleRandomJoke(numberOfJokes: Int) =
        apiService.getMultipleRandomJoke(numberOfJokes)
}
