package prieto.fernando.infinite.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InfiniteScrollListener(
    private val linearLayoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            val visibleItemCount = linearLayoutManager.childCount
            val totalItemCount = linearLayoutManager.itemCount
            val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
            if (!isLoading()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    onLoadMore()
                }
            }
        }
    }

    abstract fun onLoadMore()
    abstract fun isLoading(): Boolean

}