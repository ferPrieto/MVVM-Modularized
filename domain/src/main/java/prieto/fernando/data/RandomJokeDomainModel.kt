package prieto.fernando.data

data class RandomJokeDomainModel(
    val id: String,
    val joke: String,
    val categories: List<CategoryDomainModel>
)

enum class CategoryDomainModel {
    EXPLICIT,
    NERDY,
    UNKNOWN
}
