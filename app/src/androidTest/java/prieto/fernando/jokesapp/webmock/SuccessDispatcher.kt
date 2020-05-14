package prieto.fernando.jokesapp.webmock

import android.content.Context
import android.net.Uri
import androidx.test.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import prieto.fernando.jokesapp.webmock.AssetReaderUtil.asset

const val RANDOM_JOKE = "/jokes/random"
const val CUSTOM_JOKE = "/jokes/random?firstName=Fernando&lastName=Prieto"
const val INFINITE_JOKES = "/jokes/random/12"
const val RANDOM_JOKE_SUCCESS = "random_joke_success.json"
const val CUSTOM_JOKE_SUCCESS = "custom_joke_success.json"
const val INFINITE_JOKES_SUCCESS = "joke_list_success.json"

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
) : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        RANDOM_JOKE to RANDOM_JOKE_SUCCESS,
        CUSTOM_JOKE to CUSTOM_JOKE_SUCCESS,
        INFINITE_JOKES to INFINITE_JOKES_SUCCESS
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            val responseBody = asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}