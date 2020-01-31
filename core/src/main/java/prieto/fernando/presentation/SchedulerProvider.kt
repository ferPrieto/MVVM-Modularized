package prieto.fernando.presentation

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun <T> doOnIoObserveOnMainSingle(): SingleTransformer<T, T>
}

abstract class BaseSchedulerProvider : SchedulerProvider {
    override fun <T> doOnIoObserveOnMainSingle(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
