package prieto.fernando.model

data class MultipleRandomJokeResponse(
    val type: String,
    val value: List<MultipleJokeResponse>
)

data class MultipleJokeResponse(
    val id: String,
    val joke: String,
    val categories: List<String>
)
