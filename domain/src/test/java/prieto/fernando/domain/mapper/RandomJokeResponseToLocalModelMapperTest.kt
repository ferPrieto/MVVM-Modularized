package prieto.fernando.domain.mapper

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import prieto.fernando.mapper.RandomJokeResponseToLocalModelMapper
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.model.JokeResponse
import prieto.fernando.model.RandomJokeLocalModel
import prieto.fernando.model.RandomJokeResponse

class RandomJokeResponseToLocalModelMapperTest {
    private lateinit var cut: RandomJokeResponseToLocalModelMapper

    @Before
    fun setUp() {
        cut = RandomJokeResponseToLocalModelMapper()
    }

    @Test
    fun `Given RandomJokeResponse when toLocal then expected result`() {
        // Given
        val type = "some Type"
        val id = "some Id"
        val joke = "nice joke"
        val categoryResponseModels = listOf("explicit", "nerdy")
        val categoryLocalModels = listOf(CategoryLocalModel.EXPLICIT, CategoryLocalModel.NERDY)
        val randomJokeResponse = RandomJokeResponse(
            type,
            JokeResponse(
                id,
                joke,
                categoryResponseModels
            )
        )
        val expected = RandomJokeLocalModel(
            id,
            joke,
            categoryLocalModels
        )

        // When
        val actualValue = cut.toLocal(randomJokeResponse)

        // Then
        assertEquals(expected, actualValue)
    }
}
