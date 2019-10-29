package prieto.fernando.mapper

import dagger.Reusable
import javax.inject.Inject
import prieto.fernando.data.CategoryDomainModel
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.model.RandomJokeLocalModel

@Reusable
class RandomJokeLocalToDomainModelMapper @Inject constructor() {
    fun toDomain(randomJokeResponse: RandomJokeLocalModel) =
        RandomJokeDomainModel(
            id = randomJokeResponse.id,
            joke = randomJokeResponse.joke,
            categories = getDomainCategories(randomJokeResponse.categories)
        )

    private fun getDomainCategories(categories: List<CategoryLocalModel>) =
        categories.map { category ->
            when (category) {
                CategoryLocalModel.EXPLICIT -> CategoryDomainModel.EXPLICIT
                CategoryLocalModel.NERDY -> CategoryDomainModel.NERDY
                else -> CategoryDomainModel.UNKNOWN
            }
        }
}
