package prieto.fernando.jokesapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class JokesApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        appComponent.inject(this)
        return appComponent
    }
}
