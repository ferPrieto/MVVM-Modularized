package prieto.fernando.jokesapp.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.custom.customFragmentRobot
import prieto.fernando.jokesapp.infinite.infiniteFragmentRobot
import prieto.fernando.jokesapp.utils.TestConfigurationRule
import prieto.fernando.jokesapp.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @get:Rule
    val espressoRule = TestConfigurationRule()

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

        dashboardFragmentRobot {
            assertButtonRandomJokeDisplayed()
            clickButtonRandomJoke()
            assertDialogViewRandomJokeDisplayed()
        }
    }

    @Test
    fun dismissesRandomJokeDialogAfterOk() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            assertButtonRandomJokeDisplayed()
            clickButtonRandomJoke()
            assertDialogViewRandomJokeDisplayed()
            clickDismissButtonDialog()
        }
    }

    @Test
    fun openCustomJokeScreen() {
        dashboardFragmentRobot {
            assertButtonCustomJokeDisplayed()
            clickButtonCustomJoke()
        }

        customFragmentRobot {
            assertFirstNameEditTextViewDisplayed()
        }
    }

    @Test
    fun openInfiniteJokesScreen() {
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            assertButtonInfiniteJokesDisplayed()
            clickButtonInfiniteJokes()
        }

        infiniteFragmentRobot {
            assertRecyclerViewDisplayed()
        }
    }
}
