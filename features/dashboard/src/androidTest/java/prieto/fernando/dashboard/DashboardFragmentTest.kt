package prieto.fernando.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.TestConfigurationRule
import prieto.fernando.jokesapp.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    @get:Rule
    val espressoRule = prieto.fernando.jokesapp.TestConfigurationRule()

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
        mockWebServer.dispatcher = prieto.fernando.jokesapp.webmock.SuccessDispatcher()

        dashboardFragmentRobot {
            assertButtonRandomJokeDisplayed()
            clickButtonRandomJoke()
            assertDialogViewRandomJokeDisplayed()
        }
    }

    @Test
    fun dismissesRandomJokeDialogAfterOk() {
        mockWebServer.dispatcher = prieto.fernando.jokesapp.webmock.SuccessDispatcher()

        dashboardFragmentRobot {
            assertButtonRandomJokeDisplayed()
            clickButtonRandomJoke()
            assertDialogViewRandomJokeDisplayed()
            clickDismissButtonDialog()
        }
    }

    /*@Test
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
    }*/
}
