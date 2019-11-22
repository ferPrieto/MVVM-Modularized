package prieto.fernando.jokesapp.dashboard

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.custom.CustomFragmentRobot
import prieto.fernando.jokesapp.infinite.InfiniteFragmentRobot
import prieto.fernando.jokesapp.view.MainActivity
import prieto.fernando.jokesapp.webmock.SuccessDispatcher
import prieto.fernando.jokesapp.webmock.injectTestConfiguration

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

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
        injectTestConfiguration {}
        launchActivity<MainActivity>()
        mockWebServer.dispatcher = SuccessDispatcher()

        DashboardFragmentRobot()
            .assertButtonRandomJokeDisplayed()
            .clickButtonRandomJoke()
            .assertDialogViewRandomJokeDisplayed()
    }

    @Test
    fun dismissesRandomJokeDialogAfterOk() {
        injectTestConfiguration {}
        launchActivity<MainActivity>()
        mockWebServer.dispatcher = SuccessDispatcher()

        DashboardFragmentRobot()
            .assertButtonRandomJokeDisplayed()
            .clickButtonRandomJoke()
            .assertDialogViewRandomJokeDisplayed()
            .clickDismissButtonDialog()
    }

    @Test
    fun openCustomJokeScreen() {
        launchActivity<MainActivity>()

        DashboardFragmentRobot()
            .assertButtonCustomJokeDisplayed()
            .clickButtonCustomJoke()

        CustomFragmentRobot()
            .assertFirstNameEditTextViewDisplayed()
    }

    @Test
    fun openInfiniteJokesScreen() {
        injectTestConfiguration {}
        launchActivity<MainActivity>()
        mockWebServer.dispatcher = SuccessDispatcher()

        DashboardFragmentRobot()
            .assertButtonInfiniteJokesDisplayed()
            .clickButtonInfiniteJokes()

        InfiniteFragmentRobot()
            .assertRecyclerViewDisplayed()
    }
}
