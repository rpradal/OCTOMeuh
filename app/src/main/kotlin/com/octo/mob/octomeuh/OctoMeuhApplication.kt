package com.octo.mob.octomeuh

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import com.octo.mob.octomeuh.transversal.injection.AppComponent
import io.fabric.sdk.android.Fabric
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
        initFabric()

        analyticsManager.logActivityStarted()
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------
    private fun initDagger() {
        appComponent = AppComponent.init(this)
        appComponent.inject(this)
    }

    private fun initFabric() {
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics(), Answers());
        }
    }
}