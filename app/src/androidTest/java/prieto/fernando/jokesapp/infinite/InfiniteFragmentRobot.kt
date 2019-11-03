package prieto.fernando.jokesapp.infinite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import prieto.fernando.jokesapp.R

class InfiniteFragmentRobot {

    fun assertRecyclerViewDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    companion object {
        private val recyclerViewMatcher = withId(R.id.infinite_jokes_recycler)
    }
}