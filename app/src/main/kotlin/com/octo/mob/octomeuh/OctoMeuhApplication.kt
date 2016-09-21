package com.octo.mob.octomeuh

import android.app.Application
import com.octo.mob.octomeuh.transversal.injection.AppComponent

class OctoMeuhApplication : Application() {

    // ---------------------------------
    // CONSTANTS
    // ---------------------------------

    companion object {
        lateinit var appComponent: AppComponent
    }

    // ---------------------------------
    // LIFECYCLE
    // ---------------------------------

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------
    private fun initDagger() {
        appComponent = AppComponent.init(this)
        appComponent.inject(this)
    }

}