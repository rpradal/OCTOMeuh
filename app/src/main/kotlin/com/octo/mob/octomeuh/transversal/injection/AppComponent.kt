package com.octo.mob.octomeuh.transversal.injection

import android.media.AudioManager
import com.octo.mob.octomeuh.OctoMeuhApplication
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(octoMeuhApplication: OctoMeuhApplication)

    fun analyticsManager(): AnalyticsManager
    fun preferencePersistor(): PreferencesPersistor
    fun audioManager(): AudioManager

    companion object Initializer {

        internal fun init(app: OctoMeuhApplication): AppComponent {

            return DaggerAppComponent.builder()
                    .appModule(AppModule(app))
                    .build()
        }
    }

}