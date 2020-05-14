package prieto.fernando.custom.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import prieto.fernando.custom.CustomJokeFragment
import prieto.fernando.custom.CustomJokeViewModel
import prieto.fernando.vm.ViewModelProviderFactory

@Module
abstract class CustomModule {
    @ContributesAndroidInjector
    internal abstract fun bindCustomJokeFragment(): CustomJokeFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideCustomJokeViewModelFactory(customJokeViewModel: CustomJokeViewModel): ViewModelProviderFactory<CustomJokeViewModel> {
            return ViewModelProviderFactory(customJokeViewModel)
        }
    }
}
