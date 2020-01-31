package prieto.fernando.jokesapp.custom

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.dashboard.dashboardFragmentRobot
import prieto.fernando.android_test.utils.TestConfigurationRule
import prieto.fernando.android_test.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class CustomFragmentTest {

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

    @Test
    fun textInputsNotPassingCriteria() {
        prieto.fernando.dashboard.dashboardFragmentRobot {
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
        prieto.fernando.dashboard.dashboardFragmentRobot {
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
        prieto.fernando.dashboard.dashboardFragmentRobot {
            assertButtonCustomJokeDisplayed()
            clickButtonCustomJoke()
        }

        mockWebServer.dispatcher = prieto.fernando.android_test.webmock.SuccessDispatcher()

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

        prieto.fernando.dashboard.dashboardFragmentRobot {
            assertDialogViewCustomJokeDisplayed()
        }
    }
}