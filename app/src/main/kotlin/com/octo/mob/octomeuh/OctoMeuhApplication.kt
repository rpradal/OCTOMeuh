package com.octo.mob.octomeuh

import android.app.Application
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import com.octo.mob.octomeuh.transversal.injection.AppComponent
import javax.inject.Inject

class OctoMeuhApplication : Application() {

    // ---------------------------------
    // CONSTANTS
    // ---------------------------------

    companion object {
        lateinit var appComponent: AppComponent
    }

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    @Inject
    lateinit var analyticsManager : AnalyticsManager

    // ---------------------------------
    // LIFECYCLE
    // ---------------------------------

    override fun onCreate() {
        super.onCreate()

        initDagger()

        analyticsManager.logActivityStarted()
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------
    private fun initDagger() {
        appComponent = AppComponent.init(this)
        appComponent.inject(this)
    }

}