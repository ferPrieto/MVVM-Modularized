package prieto.fernando.presentation.mapper

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import prieto.fernando.data.CategoryDomainModel
import prieto.fernando.data.RandomJokeDomainModel
import prieto.fernando.model.CategoryUiModel
import prieto.fernando.model.RandomJokeUiModel

class RandomJokeDomainToUiModelMapperTest {
    private lateinit var cut: prieto.fernando.RandomJokeDomainToUiModelMapper

    @Before
    fun setUp() {
        cut = prieto.fernando.RandomJokeDomainToUiModelMapper()
    }

    @Test
    fun `Given RandomJokeDomainModel when toUi then return RandomJokeUiModel`() {
        // Given
        val randomJokeDomainModel = RandomJokeDomainModel(
            "some Id",
            "iOS is a good OS",
            listOf(CategoryDomainModel.NERDY, CategoryDomainModel.EXPLICIT)
        )

        val expectedResult = prieto.fernando.model.RandomJokeUiModel(
            "some Id",
            "iOS is a good OS",
            listOf(
                prieto.fernando.model.CategoryUiModel.NERDY,
                prieto.fernando.model.CategoryUiModel.EXPLICIT
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

        val expectedResult = prieto.fernando.model.RandomJokeUiModel(
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
