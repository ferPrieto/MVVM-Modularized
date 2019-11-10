package prieto.fernando.jokesapp.custom

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import prieto.fernando.jokesapp.dashboard.DashboardFragmentRobot
import prieto.fernando.jokesapp.view.MainActivity

@RunWith(AndroidJUnit4::class)
class CustomFragmentTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    fun tearDown(){
        DashboardFragmentRobot()
            .assertButtonCustomJokeDisplayed()
            .clickButtonCustomJoke()
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
            .inputFirstNameEditTextView("Steve")
            .assertLastNameEditTextViewDisplayed()
            .clickLastNameEditTextView()
            .inputLastNameEditTextView("Cook")
            .enabledDoneButton()
    }

    @Test
    fun setCustomMessageAndDialogViewPrompted() {

        CustomFragmentRobot()
            .assertFirstNameEditTextViewDisplayed()
            .clickFirstNameEditTextView()
            .inputFirstNameEditTextView("Steve")
            .assertLastNameEditTextViewDisplayed()
            .clickLastNameEditTextView()
            .inputLastNameEditTextView("Cook")
            .enabledDoneButton()
            .clickDoneButton()

        DashboardFragmentRobot()
            .assertDialogViewCustomJokeDisplayed()
    }
}