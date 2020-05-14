package prieto.fernando.jokesapp.infinite

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.infinite.BuildConfig
import prieto.fernando.jokesapp.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class InfiniteFragmentTest {

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
    fun justTwelveItemsListed() {
        mockWebServer.dispatcher = SuccessDispatcher()

        prieto.fernando.dashboard.dashboardFragmentRobot {
            assertButtonInfiniteJokesDisplayed()
            clickButtonInfiniteJokes()
        }

        infiniteFragmentRobot {
            assertRecyclerViewDisplayed()
            assertFirstItemsGroup()
        }
    }

    @Test
    fun clickItem() {
        mockWebServer.dispatcher = SuccessDispatcher()

        prieto.fernando.dashboard.dashboardFragmentRobot {
            assertButtonInfiniteJokesDisplayed()
            clickButtonInfiniteJokes()
        }

        infiniteFragmentRobot {
            assertRecyclerViewDisplayed()
            clickItem(1)
        }
    }
}