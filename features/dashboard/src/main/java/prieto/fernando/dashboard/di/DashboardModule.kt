package prieto.fernando.dashboard.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import prieto.fernando.dashboard.DashboardFragment
import prieto.fernando.dashboard.DashboardViewModel
import prieto.fernando.vm.ViewModelProviderFactory

@Module()
abstract class DashboardModule {
    @ContributesAndroidInjector
    internal abstract fun bindDashboardFragment(): DashboardFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideDashboardViewModelFactory(dashboardViewModel: DashboardViewModel): ViewModelProviderFactory<DashboardViewModel> {
            return ViewModelProviderFactory(dashboardViewModel)
        }
    }
}
