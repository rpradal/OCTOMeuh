package com.octo.mob.octomeuh.countdown.presenter

import android.os.CountDownTimer
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import com.octo.mob.octomeuh.countdown.screen.CountDownScreen
import com.octo.mob.octomeuh.countdown.utils.CountDownListener
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverter
import com.octo.mob.octomeuh.countdown.utils.SecondCountDownTimer
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import com.octo.mob.octomeuh.transversal.BasePresenterImpl
import com.octo.mob.octomeuh.transversal.withNullable

open class CountDownPresenterImpl(val analyticsManager: AnalyticsManager,
                                  val humanlyReadableDurationsConverter: HumanlyReadableDurationsConverter,
                                  val preferencesPersistor: PreferencesPersistor) : CountDownPresenter,
        CountDownListener,
        BasePresenterImpl<CountDownScreen>() {

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    internal var countDownTimer: CountDownTimer? = null
    internal var speakerCounter = 0

    // ---------------------------------
    // INTERFACE IMPLEMENTATION
    // ---------------------------------

    override fun startTimer() {
        withNullable(screen) {
            setTimerVisibility(true)

            setStopAvailability(true)
            setTimerValue(humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(getInitialCountDownValue()))

            setNextAttendeeVisibility(true)
            setStartVisibility(false)

            startChronometer()
            setFooterVisibility(true)
            setSpeakerCounter(speakerCounter)

            keepAwake(true)
        }

        restartTimer()
    }

    override fun onNewSpeakerCountDown() {
        restartTimer()
        increaseSpeakerCounter()
    }

    override fun getInitialCountDownValue(): Int {
        return preferencesPersistor.getInitialDuration()
    }

    override fun onCountDownOver() {
        screen?.notifyTimerFinished()

        when (preferencesPersistor.getRepetitionMode()) {
            RepetitionMode.STEP_BY_STEP -> screen?.switchTimerToFinishedMode()
            RepetitionMode.LOOP -> onNewSpeakerCountDown()
        }
    }

    override fun onValueUpdated(countValueInSeconds: Long) {
        screen?.setTimerValue(humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(countValueInSeconds.toInt()))
    }

    override fun onCancelMeeting() {
        withNullable(screen) {
            setTimerVisibility(false)
            setStopAvailability(false)

            setNextAttendeeVisibility(false)
            setStartVisibility(true)

            keepAwake(false)
        }

        countDownTimer?.cancel()
        screen?.setFooterVisibility(false)

        speakerCounter = 0
    }

    override fun onActionFeedbackClicked() {
        analyticsManager.logSendFeedback()
        screen?.sendFeedbackEmailAction()
    }

    // ---------------------------------
    // INTERNAL METHODS
    // ---------------------------------

    open internal fun generateNewSecondsTimer() = SecondCountDownTimer(getInitialCountDownValue(), this)

    open internal fun restartTimer() {
        screen?.switchTimerToNormalMode()

        countDownTimer?.cancel()
        countDownTimer = generateNewSecondsTimer()
        countDownTimer?.start()
    }

    open internal fun increaseSpeakerCounter() {
        speakerCounter++
        screen?.setSpeakerCounter(speakerCounter)
    }
}

