package prieto.fernando.jokesapp.detail

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import prieto.fernando.jokesapp.R

fun detailFragmentRobot(func: DetailFragmentRobot.() -> Unit) =
    DetailFragmentRobot().apply { func() }

class DetailFragmentRobot {
    fun assertJokeTextViewDisplayed() = apply {
        Espresso.onView(DetailFragmentRobot.selectedJokeTextViewMatcher)
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun assertJokeTex(joke: String) = apply {
        Espresso.onView(selectedJokeTextViewMatcher)
            .check(ViewAssertions.matches(ViewMatchers.withText(joke)))
    }

    companion object {
        private val selectedJokeTextViewMatcher = withId(R.id.selected_joke)
    }
}