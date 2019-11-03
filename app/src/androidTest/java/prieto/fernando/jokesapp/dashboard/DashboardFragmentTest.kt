package prieto.fernando.jokesapp.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.custom.CustomFragmentRobot
import prieto.fernando.jokesapp.infinite.InfiniteFragmentRobot
import prieto.fernando.jokesapp.view.MainActivity

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

    @Test
    fun openCustomJokeScreen() {
        DashboardFragmentRobot()
            .assertButtonCustomJokeDisplayed()
            .clickButtonCustomJoke()

        CustomFragmentRobot()
            .assertFirstEditTextViewDisplayed()
    }

    @Test
    fun openInfiniteJokesScreen() {
        DashboardFragmentRobot()
            .assertButtonInfiniteJokesDisplayed()
            .clickButtonInfiniteJokes()

        InfiniteFragmentRobot()
            .assertRecyclerViewDisplayed()
    }
}
