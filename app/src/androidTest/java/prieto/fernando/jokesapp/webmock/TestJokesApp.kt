package prieto.fernando.jokesapp.webmock

import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.jokesapp.JokesApp

class TestJokesApp : JokesApp() {
    override val baseUrl: String
        get() = "http://127.0.0.1:${BuildConfig.PORT}"
}