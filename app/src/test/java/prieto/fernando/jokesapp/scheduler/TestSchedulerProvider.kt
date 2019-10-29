package prieto.fernando.jokesapp.scheduler

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import prieto.fernando.presentation.BaseSchedulerProvider

class TestSchedulerProvider : prieto.fernando.presentation.BaseSchedulerProvider() {
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun ui(): Scheduler = Schedulers.trampoline()
    override fun computation(): Scheduler = Schedulers.trampoline()
    override fun newThread(): Scheduler = Schedulers.trampoline()
}
