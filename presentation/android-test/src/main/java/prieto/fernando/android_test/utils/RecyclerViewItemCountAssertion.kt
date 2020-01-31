package prieto.fernando.android_test.utils

import android.view.View
import org.hamcrest.MatcherAssert.assertThat

class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        noViewFoundException?.let {
            throw  noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter?.itemCount, Matchers.`is`(expectedCount))
    }

}