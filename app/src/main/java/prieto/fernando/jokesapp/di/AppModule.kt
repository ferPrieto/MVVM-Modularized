package prieto.fernando.jokesapp.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import prieto.fernando.jokesapp.JokesApp
import javax.inject.Singleton
import prieto.fernando.presentation.AppSchedulerProvider
import prieto.fernando.presentation.SchedulerProvider
import javax.inject.Named

@Module
open class AppModule {

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideResources(app: Application): Resources = app.resources

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

}
