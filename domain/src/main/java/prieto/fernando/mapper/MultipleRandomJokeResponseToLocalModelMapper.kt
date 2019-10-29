package prieto.fernando.mapper

import dagger.Reusable
import javax.inject.Inject
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.model.RandomJokeLocalModel
import prieto.fernando.model.MultipleRandomJokeResponse

@Reusable
class MultipleRandomJokeResponseToLocalModelMapper @Inject constructor() {
    fun toLocal(multipleRandomJokeResponse: MultipleRandomJokeResponse) =
        multipleRandomJokeResponse.value.map { multipleJokeResponse ->
            RandomJokeLocalModel(
                id = multipleJokeResponse.id,
                joke = multipleJokeResponse.joke,
                categories = getLocalCategories(multipleJokeResponse.categories)
            )
        }

    private fun getLocalCategories(categories: List<String>) =
        categories.map { category ->
            when (category) {
                "explicit" -> CategoryLocalModel.EXPLICIT
                "nerdy" -> CategoryLocalModel.NERDY
                else -> CategoryLocalModel.UNKNOWN
            }
        }
}
