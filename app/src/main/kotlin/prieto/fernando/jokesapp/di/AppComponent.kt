package prieto.fernando.jokesapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import di.RepositoryModule
import javax.inject.Singleton
import prieto.fernando.di.ChuckNorrisApiModule
import prieto.fernando.di.NetworkModule
import prieto.fernando.jokesapp.JokesApp

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ChuckNorrisApiModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        MainActivityModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: JokesApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}
