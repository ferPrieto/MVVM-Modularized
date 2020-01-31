package prieto.fernando.jokesapp.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import prieto.fernando.jokesapp.view.MainActivity
import prieto.fernando.custom.CustomJokeFragment
import prieto.fernando.dashboard.DashboardFragment
import prieto.fernando.detail.DetailFragment
import prieto.fernando.detail.DetailViewModel
import prieto.fernando.infinite.InfiniteJokesFragment
import prieto.fernando.presentation.ViewModelProviderFactory
import prieto.fernando.custom.CustomJokeViewModel
import prieto.fernando.dashboard.DashboardViewModel
import prieto.fernando.infinite.InfiniteJokesViewModel
import prieto.fernando.presentation.main.MainViewModel

@Module
internal abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindDashboardFragment(): prieto.fernando.dashboard.DashboardFragment

    @ContributesAndroidInjector
    internal abstract fun bindCustomJokeFragment(): prieto.fernando.custom.CustomJokeFragment

    @ContributesAndroidInjector
    internal abstract fun bindInfiniteJokesFragment(): prieto.fernando.infinite.InfiniteJokesFragment

    @ContributesAndroidInjector
    internal abstract fun bindDetailFragment(): prieto.fernando.detail.DetailFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideMainViewModelFactory(viewModel: MainViewModel): ViewModelProviderFactory<MainViewModel> {
            return ViewModelProviderFactory(viewModel)
        }

        @Provides
        @JvmStatic
        internal fun provideDashboardViewModelFactory(dashboardViewModel: prieto.fernando.dashboard.DashboardViewModel): ViewModelProviderFactory<prieto.fernando.dashboard.DashboardViewModel> {
            return ViewModelProviderFactory(dashboardViewModel)
        }

        @Provides
        @JvmStatic
        internal fun provideCustomJokeViewModelFactory(customJokeViewModel: prieto.fernando.custom.CustomJokeViewModel): ViewModelProviderFactory<prieto.fernando.custom.CustomJokeViewModel> {
            return ViewModelProviderFactory(customJokeViewModel)
        }

        @Provides
        @JvmStatic
        internal fun provideInfiniteJokesViewModelFactory(infiniteJokesViewModel: prieto.fernando.infinite.InfiniteJokesViewModel): ViewModelProviderFactory<prieto.fernando.infinite.InfiniteJokesViewModel> {
            return ViewModelProviderFactory(infiniteJokesViewModel)
        }

        @Provides
        @JvmStatic
        internal fun provideDetailViewModelFactory(detailViewModel: prieto.fernando.detail.DetailViewModel): ViewModelProviderFactory<prieto.fernando.detail.DetailViewModel> {
            return ViewModelProviderFactory(detailViewModel)
        }
    }
}
