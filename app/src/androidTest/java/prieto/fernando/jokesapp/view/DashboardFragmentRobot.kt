package prieto.fernando.jokesapp.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import prieto.fernando.jokesapp.R

class DashboardFragmentRobot {

    fun waitForCondition(idlingResource: IdlingResource?) = apply {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    fun assertButtonRandomJokeDisplayed() = apply {
        onView(buttonRandomJokeMatcher).check(matches(isDisplayed()))
    }

    fun clickButtonRandomJoke() = apply {
        onView(buttonRandomJokeMatcher).perform(click())
    }

    fun assertDialogViewDisplayed() = apply {
        onView(dialogTitleViewMatcher)
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    fun clickDismissButtonDialog() = apply {
        onView(dialogButtonViewMatcher).perform(click())
    }

    companion object {
        private val buttonRandomJokeMatcher = withId(R.id.button_random_joke)
        private val dialogTitleViewMatcher = withText("Random Joke")
        private val dialogButtonViewMatcher = withText("DISMISS")
    }
}