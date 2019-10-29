package prieto.fernando.model

data class RandomJokeResponse(
    val type: String,
    val value: JokeResponse
)

data class JokeResponse(
    val id: String,
    val joke: String,
    val categories: List<String>
)
