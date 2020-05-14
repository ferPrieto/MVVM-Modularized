package prieto.fernando.main.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import prieto.fernando.dashboard.di.DashboardModule
import prieto.fernando.main.vm.MainViewModel
import prieto.fernando.vm.ViewModelProviderFactory

@Module(
    includes = [
        DashboardModule::class
    ]
)
abstract class MainActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): prieto.fernando.main.view.MainActivity

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideMainViewModelFactory(viewModel: MainViewModel): ViewModelProviderFactory<MainViewModel> {
            return ViewModelProviderFactory(viewModel)
        }
    }
}
