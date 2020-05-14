package prieto.fernando.infinite.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import prieto.fernando.infinite.InfiniteJokesFragment
import prieto.fernando.infinite.InfiniteJokesViewModel
import prieto.fernando.vm.ViewModelProviderFactory

@Module
abstract class InfiniteModule {
    @ContributesAndroidInjector
    internal abstract fun bindInfiniteJokesFragment(): InfiniteJokesFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideInfiniteJokesViewModelFactory(infiniteJokesViewModel: InfiniteJokesViewModel): ViewModelProviderFactory<InfiniteJokesViewModel> {
            return ViewModelProviderFactory(infiniteJokesViewModel)
        }
    }
}
