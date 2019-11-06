package prieto.fernando.jokesapp.infinite

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.dashboard.DashboardFragmentRobot
import prieto.fernando.jokesapp.view.MainActivity

@RunWith(AndroidJUnit4::class)
class InfiniteFragmentTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)


    @Before
    fun tearDown() {
        DashboardFragmentRobot()
            .assertButtonInfiniteJokesDisplayed()
            .clickButtonInfiniteJokes()
    }

    @Test
    fun justTwelveItemsListed() {
        InfiniteFragmentRobot()
            .assertRecyclerViewDisplayed()
            .assertFirstItemsGroup()
    }

    @Test
    fun thirtyItemsListedAfterScrolling() {
        InfiniteFragmentRobot()
            .assertRecyclerViewDisplayed()
            .assertScrollToEnd()
            .assertSecondItemsGroup()
    }

}