package prieto.fernando.jokesapp.custom

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.dashboard.dashboardFragmentRobot
import prieto.fernando.jokesapp.utils.TestConfigurationRule
import prieto.fernando.jokesapp.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class CustomFragmentTest {

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
    fun textInputsNotPassingCriteria() {
        dashboardFragmentRobot {
            assertButtonCustomJokeDisplayed()
            clickButtonCustomJoke()
        }

        customFragmentRobot {
            assertFirstNameEditTextViewDisplayed()
            clickFirstNameEditTextView()
            inputFirstNameEditTextView("a.08483")
            assertLastNameEditTextViewDisplayed()
            clickLastNameEditTextView()
            inputLastNameEditTextView("a")
            disabledDoneButton()
        }
    }

    @Test
    fun textInputsPassingCriteria() {
        dashboardFragmentRobot {
            assertButtonCustomJokeDisplayed()
            clickButtonCustomJoke()
        }

        customFragmentRobot {
            assertFirstNameEditTextViewDisplayed()
            clickFirstNameEditTextView()
            inputFirstNameEditTextView("Fernando")
            assertLastNameEditTextViewDisplayed()
            clickLastNameEditTextView()
            inputLastNameEditTextView("Prieto")
            enabledDoneButton()
        }
    }

    @Test
    fun setCustomMessageAndDialogViewPrompted() {
        dashboardFragmentRobot {
            assertButtonCustomJokeDisplayed()
            clickButtonCustomJoke()
        }

        mockWebServer.dispatcher = SuccessDispatcher()

        customFragmentRobot {
            assertFirstNameEditTextViewDisplayed()
            clickFirstNameEditTextView()
            inputFirstNameEditTextView("Fernando")
            assertLastNameEditTextViewDisplayed()
            clickLastNameEditTextView()
            inputLastNameEditTextView("Prieto")
            enabledDoneButton()
            clickDoneButton()
        }

        dashboardFragmentRobot {
            assertDialogViewCustomJokeDisplayed()
        }
    }
}