package prieto.fernando.domain.mapper

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import prieto.fernando.mapper.MultipleRandomJokeResponseToLocalModelMapper
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.model.MultipleJokeResponse
import prieto.fernando.model.MultipleRandomJokeResponse
import prieto.fernando.model.RandomJokeLocalModel

class MultipleRandomJokeResponseToLocalModelMapperTest {
    private lateinit var cut: MultipleRandomJokeResponseToLocalModelMapper

    @Before
    fun setUp() {
        cut = MultipleRandomJokeResponseToLocalModelMapper()
    }

    @Test
    fun `Given MultipleRandomJokeResponse when toLocal then return list of RandomJokeLocalModel`() {
        // Given
        val type = "some Type"
        val idFirst = "Id0"
        val idSecond = "Id1"
        val joke = "nice joke"
        val categoryResponseModels = listOf("explicit", "nerdy")
        val multipleRandomJokeResponse = MultipleRandomJokeResponse(
            type,
            listOf(
                MultipleJokeResponse(
                    idFirst,
                    joke,
                    categoryResponseModels
                ),
                MultipleJokeResponse(
                    idSecond,
                    joke,
                    categoryResponseModels
                )
            )
        )

        val categoryLocalModels = listOf(CategoryLocalModel.EXPLICIT, CategoryLocalModel.NERDY)
        val expected = listOf(
            RandomJokeLocalModel(
                idFirst,
                joke,
                categoryLocalModels
            ),
            RandomJokeLocalModel(
                idSecond,
                joke,
                categoryLocalModels
            )
        )

        // When
        val actualValue = cut.toLocal(multipleRandomJokeResponse)

        // Then
        assertEquals(expected, actualValue)
    }
}
