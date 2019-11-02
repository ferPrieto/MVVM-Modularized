package prieto.fernando.presentation

import prieto.fernando.presentation.scheduler.TestSchedulerProvider

fun setupViewModelForTests(baseViewModel: BaseViewModel) {
    baseViewModel.schedulerProvider = TestSchedulerProvider()
}
