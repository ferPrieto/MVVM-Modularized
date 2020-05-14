package prieto.fernando.main.di

import javax.inject.Scope
import kotlin.annotation.Retention

@Scope
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ActivityScope
