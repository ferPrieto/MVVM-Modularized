package prieto.fernando.jokesapp.di.test

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import prieto.fernando.jokesapp.BuildConfig
import prieto.fernando.presentation.AppSchedulerProvider
import prieto.fernando.presentation.SchedulerProvider
import javax.inject.Named
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideResources(app: Application): Resources = app.resources

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Named("API_URL")
    fun provideApiUrl() = "http://127.0.0.1:${BuildConfig.PORT}"
}
