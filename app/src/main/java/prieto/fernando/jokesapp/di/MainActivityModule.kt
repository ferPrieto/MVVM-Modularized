package prieto.fernando.jokesapp.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import jokesapp.dashboard.DashboardViewModel
import prieto.fernando.jokesapp.presentation.custom.CustomJokeViewModel
import prieto.fernando.jokesapp.presentation.infinite.InfiniteJokesViewModel
import prieto.fernando.jokesapp.presentation.main.MainViewModel
import prieto.fernando.jokesapp.view.MainActivity
import prieto.fernando.jokesapp.view.custom.CustomJokeFragment
import prieto.fernando.jokesapp.view.dashboard.DashboardFragment
import prieto.fernando.jokesapp.view.infinite.InfiniteJokesFragment

@Module
internal abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    internal abstract fun bindCustomJokeFragment(): CustomJokeFragment

    @ContributesAndroidInjector
    internal abstract fun bindInfiniteJokesFragment(): InfiniteJokesFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideMainViewModelFactory(viewModel: MainViewModel): prieto.fernando.presentation.ViewModelProviderFactory<MainViewModel> {
            return prieto.fernando.presentation.ViewModelProviderFactory(viewModel)
        }

        @Provides
        @JvmStatic
        internal fun provideDashboardViewModelFactory(dashboardViewModel: DashboardViewModel): prieto.fernando.presentation.ViewModelProviderFactory<DashboardViewModel> {
            return prieto.fernando.presentation.ViewModelProviderFactory(dashboardViewModel)
        }

        @Provides
        @JvmStatic
        internal fun provideCustomJokeViewModelFactory(customJokeViewModel: CustomJokeViewModel): prieto.fernando.presentation.ViewModelProviderFactory<CustomJokeViewModel> {
            return prieto.fernando.presentation.ViewModelProviderFactory(customJokeViewModel)
        }

        @Provides
        @JvmStatic
        internal fun provideInfiniteJokesViewModelFactory(infiniteJokesViewModel: InfiniteJokesViewModel): prieto.fernando.presentation.ViewModelProviderFactory<InfiniteJokesViewModel> {
            return prieto.fernando.presentation.ViewModelProviderFactory(infiniteJokesViewModel)
        }
    }
}
