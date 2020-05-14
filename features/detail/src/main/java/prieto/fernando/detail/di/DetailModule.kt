package prieto.fernando.detail.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import prieto.fernando.detail.DetailFragment
import prieto.fernando.detail.DetailViewModel
import prieto.fernando.vm.ViewModelProviderFactory

@Module
internal abstract class DetailModule {

    @ContributesAndroidInjector
    internal abstract fun bindDetailFragment(): DetailFragment

    @Module
    companion object {
        @Provides
        @JvmStatic
        internal fun provideDetailViewModelFactory(detailViewModel: DetailViewModel): ViewModelProviderFactory<DetailViewModel> {
            return ViewModelProviderFactory(detailViewModel)
        }
    }
}