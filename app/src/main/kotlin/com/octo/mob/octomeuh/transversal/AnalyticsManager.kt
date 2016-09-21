package com.octo.mob.octomeuh.transversal

import android.content.Context


interface AnalyticsManager {
    fun logActivityStarted()

    fun logLogoClick()

    fun logSendFeedback()

    fun logChangeCountDownDuration(durationInSeconds: Int)

    fun logOctoPoweredClick()
}

class AnswersAnalyticsManager(val context: Context) : AnalyticsManager {
    override fun logActivityStarted() {
    }

    override fun logLogoClick() {
    }

    override fun logSendFeedback() {
    }

    override fun logChangeCountDownDuration(durationInSeconds: Int) {
    }

    override fun logOctoPoweredClick() {
    }
}
