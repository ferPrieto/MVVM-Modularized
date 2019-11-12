package prieto.fernando.jokesapp.di.test

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import di.RepositoryModule
import prieto.fernando.di.ChuckNorrisApiModule
import prieto.fernando.di.NetworkModule
import prieto.fernando.jokesapp.di.AppComponent
import prieto.fernando.jokesapp.di.MainActivityModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        TestAppModule::class,
        ChuckNorrisApiModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        MainActivityModule::class]
)
interface TestAppComponent : AppComponent {

    fun inject(app: TestJokesApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): TestAppComponent
    }
}
