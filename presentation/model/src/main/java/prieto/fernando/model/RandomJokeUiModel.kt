package prieto.fernando.model

data class RandomJokeUiModel(
    val id: String,
    val joke: String,
    val categories: List<CategoryUiModel>
)

enum class CategoryUiModel {
    EXPLICIT,
    NERDY,
    UNKNOWN
}
