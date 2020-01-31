package prieto.fernando.detail

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    @get:Rule
    val espressoRule = prieto.fernando.android_test.utils.TestConfigurationRule()

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(BuildConfig.PORT)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    /*  @Test
      fun textFieldShown() {
          mockWebServer.dispatcher = prieto.fernando.android_test.webmock.SuccessDispatcher()

          prieto.fernando.dashboard.dashboardFragmentRobot {
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
      }*/
}