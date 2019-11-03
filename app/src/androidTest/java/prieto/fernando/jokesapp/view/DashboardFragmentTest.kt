package prieto.fernando.jokesapp.view

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.idling.ViewVisibilityIdlingResource

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    private var dialogViewTitleIdlingResource: ViewVisibilityIdlingResource? = null

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(dialogViewTitleIdlingResource)
    }

    @Test
    fun openRandomJokeDialog() {
        DashboardFragmentRobot()
            .assertButtonRandomJokeDisplayed()
            .clickButtonRandomJoke()
            .assertDialogViewDisplayed()
    }
}
