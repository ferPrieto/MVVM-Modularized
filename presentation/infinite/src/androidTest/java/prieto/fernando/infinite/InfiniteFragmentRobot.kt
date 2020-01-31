package prieto.fernando.infinite

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import prieto.fernando.android_test.utils.RecyclerViewItemCountAssertion

fun infiniteFragmentRobot(func: InfiniteFragmentRobot.() -> Unit) =
    InfiniteFragmentRobot().apply { func() }

class InfiniteFragmentRobot {

    fun assertRecyclerViewDisplayed() = apply {
        onView(recyclerViewMatcher).check(matches(isDisplayed()))
    }

    fun assertFirstItemsGroup() = apply {
        onView(recyclerViewMatcher).check(
             RecyclerViewItemCountAssertion(12)
        )
    }

    fun clickItem(position: Int) = apply {
        val itemMatcher = prieto.fernando.android_test.utils.RecyclerViewMatcher(recyclerViewId).atPosition(position)
        onView(itemMatcher).perform(ViewActions.click())
    }

    companion object {
        private const val recyclerViewId = R.id.infinite_jokes_recycler

        private val recyclerViewMatcher = withId(R.id.infinite_jokes_recycler)
    }
}