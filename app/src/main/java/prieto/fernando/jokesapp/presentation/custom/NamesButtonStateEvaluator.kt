package prieto.fernando.jokesapp.presentation.custom

import dagger.Reusable
import java.util.regex.Pattern
import javax.inject.Inject

@Reusable
class NamesButtonStateEvaluator @Inject constructor() {

    fun shouldEnableButton(
        firstName: String?,
        lastName: String?
    ): Boolean {
        val notEmpty =
            isNotNullOrBlank(firstName) && isNotNullOrBlank(lastName)
        val onlyContainLetters =
            NAME_REGEX.matcher(firstName).matches() && NAME_REGEX.matcher(lastName).matches()
        val rightLength = isInTheRange(firstName) && isInTheRange(lastName)
        return notEmpty &&
                onlyContainLetters &&
                rightLength
    }

    private fun isNotNullOrBlank(value: String?) = !value.isNullOrBlank()

    private fun isInTheRange(value: String?) =
        value?.length ?: 0 in NAME_MIN_LENGTH..NAME_MAX_LENGTH

    companion object {
        const val NAME_MAX_LENGTH = 25
        const val NAME_MIN_LENGTH = 3
        val NAME_REGEX: Pattern = Pattern.compile("[a-zA-Z ]+")
    }
}
