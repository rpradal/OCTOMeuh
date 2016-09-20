package com.octo.mob.octomeuh.settings.injection

import com.octo.mob.octomeuh.OctoMeuhApplication
import com.octo.mob.octomeuh.settings.screen.SettingsActivity
import com.octo.mob.octomeuh.transversal.injection.AppComponent
import dagger.Component
import javax.inject.Scope

@Component(modules = arrayOf(SettingsModule::class), dependencies = arrayOf(AppComponent::class))
@SettingsScope
interface SettingsComponent {
    fun inject(settingsActivity: SettingsActivity)

    companion object Initializer {
        fun init(): SettingsComponent {
            return DaggerSettingsComponent
                    .builder()
                    .appComponent(OctoMeuhApplication.appComponent)
                    .settingsModule(SettingsModule())
                    .build()
        }
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class SettingsScope