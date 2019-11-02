package prieto.fernando.source

import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import prieto.fernando.model.RandomJokeLocalModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokesLocalSource @Inject constructor() {
    private val customJokeSubject = BehaviorSubject.createDefault(getEmptyRandomJoke())
    private var hasCustomJokesValidData = false

    fun setCustomJoke(randomJokeLocalModel: RandomJokeLocalModel) {
        hasCustomJokesValidData = true
        customJokeSubject.onNext(randomJokeLocalModel)
    }

    fun getCustomJoke(): Single<RandomJokeLocalModel> =
        customJokeSubject.distinctUntilChanged().firstOrError()

    fun hasCustomJokesValidData(): Boolean = hasCustomJokesValidData

    fun resetData(): Single<Unit> {
        hasCustomJokesValidData = false
        customJokeSubject.onNext(getEmptyRandomJoke())
        return Single.just(Unit)
    }

    private fun getEmptyRandomJoke() = RandomJokeLocalModel("", "", emptyList())
}
