package prieto.fernando.jokesapp.custom

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.dashboard.DashboardFragmentRobot
import prieto.fernando.jokesapp.view.MainActivity
import prieto.fernando.jokesapp.webmock.SuccessDispatcher

@RunWith(AndroidJUnit4::class)
class CustomFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup(){
        mockWebServer.start(BuildConfig.PORT)

        DashboardFragmentRobot()
            .assertButtonCustomJokeDisplayed()
            .clickButtonCustomJoke()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun textInputsNotPassingCriteria() {

        CustomFragmentRobot()
            .assertFirstNameEditTextViewDisplayed()
            .clickFirstNameEditTextView()
            .inputFirstNameEditTextView("a.08483")
            .assertLastNameEditTextViewDisplayed()
            .clickLastNameEditTextView()
            .inputLastNameEditTextView("a")
            .disabledDoneButton()
    }

    @Test
    fun textInputsPassingCriteria() {

        CustomFragmentRobot()
            .assertFirstNameEditTextViewDisplayed()
            .clickFirstNameEditTextView()
            .inputFirstNameEditTextView("Fernando")
            .assertLastNameEditTextViewDisplayed()
            .clickLastNameEditTextView()
            .inputLastNameEditTextView("Prieto")
            .enabledDoneButton()
    }

    @Test
    fun setCustomMessageAndDialogViewPrompted() {
        mockWebServer.dispatcher = SuccessDispatcher()

        CustomFragmentRobot()
            .assertFirstNameEditTextViewDisplayed()
            .clickFirstNameEditTextView()
            .inputFirstNameEditTextView("Fernando")
            .assertLastNameEditTextViewDisplayed()
            .clickLastNameEditTextView()
            .inputLastNameEditTextView("Prieto")
            .enabledDoneButton()
            .clickDoneButton()

        DashboardFragmentRobot()
            .assertDialogViewCustomJokeDisplayed()
    }
}