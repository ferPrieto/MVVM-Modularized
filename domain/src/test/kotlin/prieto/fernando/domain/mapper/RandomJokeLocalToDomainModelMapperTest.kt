package prieto.fernando.domain.mapper

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import prieto.fernando.data.CategoryDomainModel
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.mapper.RandomJokeLocalToDomainModelMapper
import prieto.fernando.model.CategoryLocalModel
import prieto.fernando.model.RandomJokeLocalModel

class RandomJokeLocalToDomainModelMapperTest {
    private lateinit var cut: RandomJokeLocalToDomainModelMapper

    @Before
    fun setUp() {
        cut = RandomJokeLocalToDomainModelMapper()
    }

    @Test
    fun `Given RandomJokeLocalModel when toDomain then return expected RandomJokeDomainModel`() {
        // Given
        val type = "some Type"
        val id = "some Id"
        val joke = "nice joke"
        val categoryLocalModels = listOf(CategoryLocalModel.EXPLICIT, CategoryLocalModel.NERDY)
        val randomJokeLocalModel = RandomJokeLocalModel(
            id,
            joke,
            categoryLocalModels
        )
        val categoryDomainModels = listOf(CategoryDomainModel.EXPLICIT, CategoryDomainModel.NERDY)
        val expected = RandomJokeDomainModel(
            id,
            joke,
            categoryDomainModels
        )

        // When
        val actualValue = cut.toDomain(randomJokeLocalModel)

        // Then
        assertEquals(expected, actualValue)
    }
}
