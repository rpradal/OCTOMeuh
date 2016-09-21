package com.octo.mob.octomeuh.transversal

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.octo.mob.octomeuh.R


interface AnalyticsManager {
    fun logLogoClick()

    fun logSendFeedback()

    fun logChangeCountDownDuration(durationInSeconds: Int)

    fun logOctoPoweredClick()
}

class AnswersAnalyticsManager(val context: Context,
                              val firebaseAnalytics: FirebaseAnalytics) : AnalyticsManager {

    override fun logLogoClick() {
        firebaseAnalytics.logEvent(context.getString(R.string.tag_logo_click), Bundle())
    }

    override fun logSendFeedback() {
        firebaseAnalytics.logEvent(context.getString(R.string.tag_send_feedback), Bundle())
    }

    override fun logChangeCountDownDuration(durationInSeconds: Int) {
        val bundle = Bundle()
        bundle.putInt(context.getString(R.string.tag_duration), durationInSeconds)
        firebaseAnalytics.logEvent(context.getString(R.string.tag_change_countdown_duration), bundle)
    }

    override fun logOctoPoweredClick() {
        firebaseAnalytics.logEvent(context.getString(R.string.tag_octo_logo_clicked), Bundle())
    }
}
