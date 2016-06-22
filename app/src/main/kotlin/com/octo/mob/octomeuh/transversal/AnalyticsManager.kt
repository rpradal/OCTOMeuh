package com.octo.mob.octomeuh.transversal

import android.content.Context
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import com.octo.mob.octomeuh.BuildConfig
import com.octo.mob.octomeuh.R


interface AnalyticsManager {
    fun logActivityStarted()

    fun logLogoClick()

    fun logSendFeedback()

    fun logChangeCountDownDuration(durationInSeconds: Int)

    fun logOctoPoweredClick()
}

class AnswersAnalyticsManager(val context: Context) : AnalyticsManager {

    // ---------------------------------
    // INTERFACE IMPLEMENTATION
    // ---------------------------------

    override fun logLogoClick() {
        val logoClickEvent = CustomEvent(context.getString(R.string.tag_logo_click))
        getAnalyticsInstance()?.logCustom(logoClickEvent)
    }

    override fun logSendFeedback() {
        val sendFeedbackEvent = CustomEvent(context.getString(R.string.tag_send_feedback))
        getAnalyticsInstance()?.logCustom(sendFeedbackEvent)
    }

    override fun logChangeCountDownDuration(durationInSeconds: Int) {
        val changeCountDownDurationEvent = CustomEvent(context.getString(R.string.tag_change_countdown_duration))
                .putCustomAttribute(context.getString(R.string.tag_duration), durationInSeconds)
        getAnalyticsInstance()?.logCustom(changeCountDownDurationEvent)
    }

    override fun logOctoPoweredClick() {
        val octoPoweredClickEvent = CustomEvent(context.getString(R.string.tag_octo_logo_clicked))
        getAnalyticsInstance()?.logCustom(octoPoweredClickEvent)
    }

    override fun logActivityStarted() {
        val appStartedEvent = CustomEvent(context.getString(R.string.tag_app_started))
        getAnalyticsInstance()?.logCustom(appStartedEvent)
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------

    private fun getAnalyticsInstance(): Answers? {
        if (!BuildConfig.DEBUG) {
            return Answers.getInstance()
        } else {
            return null
        }
    }

}
