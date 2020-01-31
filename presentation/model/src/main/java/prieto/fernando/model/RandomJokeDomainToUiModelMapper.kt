package prieto.fernando.model

import dagger.Reusable
import prieto.fernando.data.CategoryDomainModel
import prieto.fernando.data.RandomJokeDomainModel
import javax.inject.Inject

@Reusable
class RandomJokeDomainToUiModelMapper @Inject constructor() {
    fun toUi(randomJokeDomainModel: RandomJokeDomainModel) =
        RandomJokeUiModel(
            id = randomJokeDomainModel.id,
            joke = randomJokeDomainModel.joke,
            categories = getCategories(randomJokeDomainModel.categories)
        )

    private fun getCategories(categories: List<CategoryDomainModel>) =
        categories.map { categoryDomainModel ->
            when (categoryDomainModel) {
                CategoryDomainModel.EXPLICIT -> CategoryUiModel.EXPLICIT
                CategoryDomainModel.NERDY -> CategoryUiModel.NERDY
                else -> CategoryUiModel.UNKNOWN
            }
        }
}
