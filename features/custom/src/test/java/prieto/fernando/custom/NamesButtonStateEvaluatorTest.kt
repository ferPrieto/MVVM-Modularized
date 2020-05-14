package prieto.fernando.custom

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class NamesButtonStateEvaluatorTest(
    private val givenNames: Names,
    private val expected: Boolean
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(Names("", ""), false),
                arrayOf(Names("   ", "    "), false),
                arrayOf(Names("SomeName", ""), false),
                arrayOf(Names("", "SomeLastName"), false),
                arrayOf(Names("Sh", ""), false),
                arrayOf(Names("", "Nn"), false),
                arrayOf(Names("Sh", "Nn"), false),
                arrayOf(
                    Names(
                        "NameReallyReallyReallySuperLong",
                        "SomeLastName"
                    ), false
                ),
                arrayOf(
                    Names(
                        "",
                        "LastNameReallyReallyLongLongg"
                    ), false
                ),
                arrayOf(Names("SomeName", "SomeLastName"), true),
                arrayOf(Names("Six", "The"), true),
                arrayOf(
                    Names(
                        "NameReallyReallySuperLong",
                        "LastNameReallyReallyLongLongg"
                    ), false
                ),
                arrayOf(
                    Names(
                        "NameReallyReallySuperLong",
                        "LastNameReallyReallyLong"
                    ), true
                )

            )
        }
    }

    private lateinit var cut: NamesButtonStateEvaluator

    @Before
    fun setUp() {
        cut = NamesButtonStateEvaluator()
    }

    @Test
    fun `Given names when shouldEnableButton then return expected result`() {
        // When
        val actualValue = cut.shouldEnableButton(givenNames.firstName, givenNames.lastName)

        // Then
        assertEquals(expected, actualValue)
    }
}

data class Names(
    val firstName: String,
    val lastName: String
)
