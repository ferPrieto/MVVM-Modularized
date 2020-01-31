package prieto.fernando.android_test.utils

import androidx.test.core.app.launchActivity
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import prieto.fernando.jokesapp.view.MainActivity
import prieto.fernando.jokesapp.webmock.injectTestConfiguration

class TestConfigurationRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)

        injectTestConfiguration {}
        launchActivity<MainActivity>()
    }
}