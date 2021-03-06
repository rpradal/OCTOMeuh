package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.screen.CountDownScreen
import com.octo.mob.octomeuh.transversal.BasePresenter

interface CountDownPresenter : BasePresenter<CountDownScreen>{
    fun startTimer()

    fun onNewSpeakerCountDown()

    fun onCancelMeeting()

    fun getInitialCountDownValue(): Int

    fun onActionFeedbackClicked()

    fun onScreenFirstDisplay()
}

