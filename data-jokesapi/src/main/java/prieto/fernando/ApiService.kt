package prieto.fernando

import io.reactivex.Single
import prieto.fernando.model.MultipleRandomJokeResponse
import prieto.fernando.model.RandomJokeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("jokes/random")
    fun getRandomJoke(): Single<RandomJokeResponse>

    @GET("jokes/random")
    fun getRandomCustomJoke(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Single<RandomJokeResponse>

    @GET("jokes/random/{numberOfJokes}")
    fun getMultipleRandomJoke(@Path("numberOfJokes") numberOfJokes: Int):
            Single<MultipleRandomJokeResponse>
}
