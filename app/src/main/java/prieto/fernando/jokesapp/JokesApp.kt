package prieto.fernando.jokesapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import prieto.fernando.jokesapp.di.DaggerAppComponent

open class JokesApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .build()
}
