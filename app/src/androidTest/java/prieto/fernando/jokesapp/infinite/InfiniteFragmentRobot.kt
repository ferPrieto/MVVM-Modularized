package prieto.fernando.jokesapp.infinite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import prieto.fernando.jokesapp.R
import prieto.fernando.jokesapp.utils.RecyclerViewItemCountAssertion
import prieto.fernando.jokesapp.view.infinite.adapter.JokesAdapter

class InfiniteFragmentRobot {

    fun assertRecyclerViewDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    fun assertFirstItemsGroup() = apply {
        onView(recyclerViewMatcher).check(
            RecyclerViewItemCountAssertion(12)
        )
    }

    fun assertSecondItemsGroup() = apply {
        onView(recyclerViewMatcher).check(
            RecyclerViewItemCountAssertion(36)
        )
    }


    fun assertScrollToEnd() = apply {
        onView(recyclerViewMatcher).perform(
            RecyclerViewActions.scrollToPosition<JokesAdapter.JokeHolder>(12)

        )
    }

    companion object {
        private val recyclerViewMatcher = withId(R.id.infinite_jokes_recycler)
    }
}