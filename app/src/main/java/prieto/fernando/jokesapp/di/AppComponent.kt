package prieto.fernando.jokesapp.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import di.RepositoryModule
import prieto.fernando.di.ChuckNorrisApiModule
import prieto.fernando.di.NetworkModule
import prieto.fernando.jokesapp.JokesApp
import prieto.fernando.main.di.MainActivityModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ChuckNorrisApiModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        MainActivityModule::class]
)
@Singleton
interface AppComponent : AndroidInjector<JokesApp>
