package prieto.fernando.jokesapp.webmock

import prieto.fernando.di.NetworkModule
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.JokesApp
import prieto.fernando.jokesapp.di.AppComponent
import prieto.fernando.jokesapp.di.DaggerAppComponent


class TestConfigurationBuilder {
    private val baseUrl: String = "http://127.0.0.1:${BuildConfig.PORT}"

    fun inject() {
        appComponent {
            networkModule(NetworkModule(baseUrl))
        }.inject(requireTestedApplication())
    }
}

fun injectTestConfiguration(block: TestConfigurationBuilder.() -> Unit) {
    TestConfigurationBuilder().apply(block).inject()
}

private fun appComponent(block: DaggerAppComponent.Builder.() -> Unit = {}): AppComponent =
    DaggerAppComponent.builder().apply(block).build()

private fun requireTestedApplication() =
    (androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as JokesApp)