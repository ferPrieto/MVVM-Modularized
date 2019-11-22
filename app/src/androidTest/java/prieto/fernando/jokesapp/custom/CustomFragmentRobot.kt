package prieto.fernando.jokesapp.custom

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.not
import prieto.fernando.jokesapp.R

fun customFragmentRobot(func: CustomFragmentRobot.()-> Unit) = CustomFragmentRobot()
    .apply { func() }

class CustomFragmentRobot {

    fun clickFirstNameEditTextView() = apply {
        onView(firstNameEditTextViewMatcher).perform(click())
    }

    fun clickLastNameEditTextView() = apply {
        onView(lastNameEditTextViewMatcher).perform(click())
    }

    fun inputFirstNameEditTextView(text: String) = apply {
        onView(firstNameEditTextViewMatcher).perform(typeText(text))
    }

    fun inputLastNameEditTextView(text: String) = apply {
        onView(lastNameEditTextViewMatcher).perform(typeText(text))
    }

    fun assertFirstNameEditTextViewDisplayed() = apply {
        onView(firstNameEditTextViewMatcher).check(matches(isDisplayed()))
    }

    fun assertLastNameEditTextViewDisplayed() = apply {
        onView(lastNameEditTextViewMatcher).check(matches(isDisplayed()))
    }

    fun clickDoneButton() = apply {
        onView(doneButtonMatcher).perform(click())
    }

    fun enabledDoneButton() = apply {
        onView(doneButtonMatcher).check(matches(isEnabled()))
    }

    fun disabledDoneButton() = apply {
        onView(doneButtonMatcher).check(matches(not(isEnabled())))
    }

    companion object {
        private val firstNameEditTextViewMatcher = withId(R.id.first_name_text)
        private val lastNameEditTextViewMatcher = withId(R.id.last_name_text)
        private val doneButtonMatcher = withId(R.id.button_done)
    }
}