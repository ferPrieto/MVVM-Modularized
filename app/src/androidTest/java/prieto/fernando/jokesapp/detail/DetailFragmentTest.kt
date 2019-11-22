package prieto.fernando.jokesapp.detail

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.dashboard.dashboardFragmentRobot
import prieto.fernando.jokesapp.infinite.infiniteFragmentRobot
import prieto.fernando.jokesapp.view.MainActivity
import prieto.fernando.jokesapp.webmock.SuccessDispatcher
import prieto.fernando.jokesapp.webmock.injectTestConfiguration

@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {
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
    fun textFieldShown() {
        injectTestConfiguration {}
        launchActivity<MainActivity>()
        mockWebServer.dispatcher = SuccessDispatcher()

        dashboardFragmentRobot {
            assertButtonInfiniteJokesDisplayed()
            clickButtonInfiniteJokes()
        }

        infiniteFragmentRobot {
            assertRecyclerViewDisplayed()
            clickItem(1)
        }


        detailFragmentRobot {
            assertJokeTextViewDisplayed()
            assertJokeTex("Divide Chuck Norris by zero and you will in fact get one........one bad-ass that is.")
        }
    }
}