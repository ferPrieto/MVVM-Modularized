package prieto.fernando.repository

import io.reactivex.Single
import prieto.fernando.mapper.MultipleRandomJokeResponseToLocalModelMapper
import prieto.fernando.mapper.RandomJokeResponseToLocalModelMapper
import javax.inject.Inject
import prieto.fernando.source.JokesLocalSource
import prieto.fernando.data.JokesRemoteSource
import prieto.fernando.model.RandomJokeLocalModel

interface JokesRepository {
    fun randomJoke(): Single<RandomJokeLocalModel>

    fun randomCustomJoke(
        firstName: String?,
        lastName: String?
    ): Single<RandomJokeLocalModel>

    fun multipleRandomJokes(
        numberOfJokes: Int
    ): Single<List<RandomJokeLocalModel>>

    fun resetCustomRandomJoke(): Single<Unit>
}

class JokesRepositoryImpl @Inject constructor(
    private val localSource: JokesLocalSource,
    private val remoteSource: JokesRemoteSource,
    private val jokeResponseToLocalMapper: RandomJokeResponseToLocalModelMapper,
    private val multipleJokeResponseToLocalMapper: MultipleRandomJokeResponseToLocalModelMapper
) : JokesRepository {
    override fun randomJoke(): Single<RandomJokeLocalModel> = remoteSource.getRandomJoke().map { response ->
        jokeResponseToLocalMapper.toLocal(response)
    }

    override fun randomCustomJoke(
        firstName: String?,
        lastName: String?
    ): Single<RandomJokeLocalModel> = if (firstName.isNullOrBlank() || lastName.isNullOrBlank()) {
        retrieveLocalJokeOrException()
    } else {
        remoteSource.getRandomCustomJoke(firstName, lastName).map { response ->
            val randomJokeLocalModel = jokeResponseToLocalMapper.toLocal(response)
            localSource.setCustomJoke(randomJokeLocalModel)
            randomJokeLocalModel
        }
    }

    override fun multipleRandomJokes(
        numberOfJokes: Int
    ): Single<List<RandomJokeLocalModel>> = remoteSource.getMultipleRandomJoke(numberOfJokes).map { response ->
        multipleJokeResponseToLocalMapper.toLocal(response)
    }

    override fun resetCustomRandomJoke(): Single<Unit> = localSource.resetData()

    private fun retrieveLocalJokeOrException() =
        if (localSource.hasCustomJokesValidData()) {
            localSource.getCustomJoke()
        } else {
            Single.error(CustomJokesException())
        }
}

class CustomJokesException : Exception("There are no jokes saved in cache")
