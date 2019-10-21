package prieto.fernando.model

data class RandomJokeLocalModel(
    val id: String,
    val joke: String,
    val categories: List<CategoryLocalModel>
)

enum class CategoryLocalModel {
    EXPLICIT,
    NERDY,
    UNKNOWN
}
