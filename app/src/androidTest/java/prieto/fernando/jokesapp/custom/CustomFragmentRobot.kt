package prieto.fernando.jokesapp.custom

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import prieto.fernando.jokesapp.R

class CustomFragmentRobot {

    fun assertFirstEditTextViewDisplayed() = apply {
        onView(firstEditTextViewMatcher).check(matches(isDisplayed()))
    }

    companion object {
        private val firstEditTextViewMatcher = withId(R.id.first_name_text)
    }
}