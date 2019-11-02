package prieto.fernando.presentation.mapper

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import prieto.fernando.data.CategoryDomainModel
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.presentation.CategoryUiModel
import prieto.fernando.presentation.mapper.RandomJokeDomainToUiModelMapper
import prieto.fernando.presentation.RandomJokeUiModel

class RandomJokeDomainToUiModelMapperTest {
    private lateinit var cut: RandomJokeDomainToUiModelMapper

    @Before
    fun setUp() {
        cut = RandomJokeDomainToUiModelMapper()
    }

    @Test
    fun `Given RandomJokeDomainModel when toUi then return RandomJokeUiModel`() {
        // Given
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "iOS is a good OS",
            listOf(CategoryDomainModel.NERDY, CategoryDomainModel.EXPLICIT)
        )

        val expectedResult = RandomJokeUiModel(
            "some Id",
            "iOS is a good OS",
            listOf(
                CategoryUiModel.NERDY,
                CategoryUiModel.EXPLICIT
            )
        )

        // When
        val actualValue = cut.toUi(randomJokeDomainModel)

        // Then
        assertEquals(expectedResult, actualValue)
    }

    @Test
    fun `Given RandomJokeDomainModel with empty categories when toUi then return RandomJokeUiModel`() {
        // Given
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )

        val expectedResult = RandomJokeUiModel(
            "some Id",
            "iOS is a good OS",
            emptyList()
        )

        // When
        val actualValue = cut.toUi(randomJokeDomainModel)

        // Then
        assertEquals(expectedResult, actualValue)
    }
}
