package prieto.fernando.jokesapp.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import prieto.fernando.presentation.AppSchedulerProvider
import prieto.fernando.presentation.SchedulerProvider

@Module
class AppModule {
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideResources(app: Application): Resources = app.resources

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}
