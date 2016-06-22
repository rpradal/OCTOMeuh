package com.octo.mob.octomeuh.countdown.screen

interface CountDownScreen {
    fun setStopAvailability(isStopAvailable: Boolean)

    fun setTimerValue(timerValueInSeconds: Int)

    fun switchTimerToFinishedMode()

    fun setNextAttendeeVisibility(nextAtendeeVisibility: Boolean)

    fun setStartVisibility(startVisibility: Boolean)

    fun switchTimerToNormalMode()

    fun startChronometer()

    fun setSpeakerCounter(speakerCounter: Int)

    fun setFooterVisibility(footerVisibility: Boolean)

    fun setTimerVisibility(timerVisibility: Boolean)

    fun notifyTimerFinished()

    fun sendFeedbackEmailAction()

    fun keepAwake(shouldKeepAwake: Boolean)
}