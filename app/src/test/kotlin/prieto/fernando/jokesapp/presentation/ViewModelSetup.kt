package prieto.fernando.jokesapp.presentation

import prieto.fernando.mytaxiapp.scheduler.TestSchedulerProvider
import prieto.fernando.presentation.BaseViewModel

fun setupViewModelForTests(baseViewModel: BaseViewModel) {
    baseViewModel.schedulerProvider = TestSchedulerProvider()
}
