package prieto.fernando.jokesapp.dashboard

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import prieto.fernando.jokesapp.R

class DashboardFragmentRobot {

    fun assertButtonRandomJokeDisplayed() = apply {
        onView(buttonRandomJokeMatcher).check(matches(isDisplayed()))
    }

    fun clickButtonRandomJoke() = apply {
        onView(buttonRandomJokeMatcher).perform(click())
    }

    fun assertDialogViewRandomJokeDisplayed() = apply {
        onView(dialogRandomTitleViewMatcher)
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    fun assertDialogViewCustomJokeDisplayed() = apply {
        onView(dialogCustomTitleViewMatcher)
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    fun clickDismissButtonDialog() = apply {
        onView(dialogButtonViewMatcher).perform(click())
    }

    fun assertButtonCustomJokeDisplayed() = apply {
        onView(buttonCustomJokeMatcher).check(matches(isDisplayed()))
    }

    fun clickButtonCustomJoke() = apply {
        onView(buttonCustomJokeMatcher).perform(click())
    }

    fun assertButtonInfiniteJokesDisplayed() = apply {
        onView(buttonInfiniteJokesMatcher).check(matches(isDisplayed()))
    }

    fun clickButtonInfiniteJokes() = apply {
        onView(buttonInfiniteJokesMatcher).perform(click())
    }

    companion object {
        private val buttonRandomJokeMatcher = withId(R.id.button_random_joke)
        private val dialogRandomTitleViewMatcher = withText("Random Joke")
        private val dialogCustomTitleViewMatcher = withText("Custom Joke")
        private val dialogButtonViewMatcher = withText("DISMISS")
        private val buttonCustomJokeMatcher = withId(R.id.button_custom_joke)
        private val buttonInfiniteJokesMatcher = withId(R.id.button_multiple_jokes)
    }
}