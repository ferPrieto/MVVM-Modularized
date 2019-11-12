package prieto.fernando.jokesapp.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.custom.CustomFragmentRobot
import prieto.fernando.jokesapp.infinite.InfiniteFragmentRobot
import prieto.fernando.jokesapp.view.MainActivity
import prieto.fernando.jokesapp.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun openRandomJokeDialog() {
        mockWebServer.dispatcher = SuccessDispatcher()

        DashboardFragmentRobot()
            .assertButtonRandomJokeDisplayed()
            .clickButtonRandomJoke()
            .assertDialogViewRandomJokeDisplayed()
    }

    @Test
    fun dismissesRandomJokeDialogAfterOk() {
        mockWebServer.dispatcher = SuccessDispatcher()

        DashboardFragmentRobot()
            .assertButtonRandomJokeDisplayed()
            .clickButtonRandomJoke()
            .assertDialogViewRandomJokeDisplayed()
            .clickDismissButtonDialog()
    }

    @Test
    fun openCustomJokeScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        DashboardFragmentRobot()
            .assertButtonCustomJokeDisplayed()
            .clickButtonCustomJoke()

        CustomFragmentRobot()
            .assertFirstNameEditTextViewDisplayed()
    }

    @Test
    fun openInfiniteJokesScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        DashboardFragmentRobot()
            .assertButtonInfiniteJokesDisplayed()
            .clickButtonInfiniteJokes()

        InfiniteFragmentRobot()
            .assertRecyclerViewDisplayed()
    }
}
