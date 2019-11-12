package prieto.fernando.jokesapp.di.test

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TestJokesApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerTestAppComponent.builder()
            .application(this)
            .build()

        appComponent.inject(this)
        return appComponent
    }
}