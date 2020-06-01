package prieto.fernando.vm

import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler

    fun <T> doOnIoObserveOnMainSingle(): SingleTransformer<T, T>
}

abstract class BaseSchedulerProvider : SchedulerProvider {
    override fun <T> doOnIoObserveOnMainSingle(): SingleTransformer<T, T> = SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

class AppSchedulerProvider : BaseSchedulerProvider() {
    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}
