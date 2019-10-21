package prieto.fernando.mapper

import dagger.Reusable
import javax.inject.Inject
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.model.RandomJokeLocalModel
import prieto.fernando.model.RandomJokeResponse

@Reusable
class RandomJokeResponseToLocalModelMapper @Inject constructor() {
    fun toLocal(randomJokeResponse: RandomJokeResponse) =
        RandomJokeLocalModel(
            id = randomJokeResponse.value.id,
            joke = randomJokeResponse.value.joke,
            categories = getLocalCategories(randomJokeResponse.value.categories)
        )

    private fun getLocalCategories(categories: List<String>) =
        categories.map { category ->
            when (category) {
                "explicit" -> CategoryLocalModel.EXPLICIT
                "nerdy" -> CategoryLocalModel.NERDY
                else -> CategoryLocalModel.UNKNOWN
            }
        }
}
