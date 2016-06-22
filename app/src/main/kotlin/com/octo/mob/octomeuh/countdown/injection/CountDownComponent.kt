package com.octo.mob.octomeuh.countdown.injection

import com.octo.mob.octomeuh.OctoMeuhApplication
import com.octo.mob.octomeuh.countdown.screen.CountDownActivity
import com.octo.mob.octomeuh.countdown.screen.RepetitionModeDialogFragment
import com.octo.mob.octomeuh.transversal.injection.AppComponent
import dagger.Component
import javax.inject.Scope

@Component(modules = arrayOf(CountdownModule::class),
        dependencies = arrayOf(AppComponent::class))
@CountDownScope
interface CountDownComponent {

    fun inject(countDownActivity: CountDownActivity)
    fun inject(repetitionModeDialogFragment: RepetitionModeDialogFragment)

    companion object Initializer {
        fun init(): CountDownComponent {
            return DaggerCountDownComponent.builder().appComponent(OctoMeuhApplication.appComponent).countdownModule(CountdownModule()).build()
        }
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CountDownScope
