package prieto.fernando.jokesapp.view

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun openRandomJokeDialog() {
        DashboardFragmentRobot()
            .assertButtonRandomJokeDisplayed()
            .clickButtonRandomJoke()
            .assertDialogViewDisplayed()
    }

    @Test
    fun dismissesRandomJokeDialogAfterOk() {
        DashboardFragmentRobot()
            .assertButtonRandomJokeDisplayed()
            .clickButtonRandomJoke()
            .assertDialogViewDisplayed()
            .clickDismissButtonDialog()
    }
}
