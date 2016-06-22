package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.screen.RepetitionModeSelectorScreen
import com.octo.mob.octomeuh.transversal.BasePresenter

interface RepetitionModeSelectorPresenter : BasePresenter<RepetitionModeSelectorScreen> {
    fun onLoopModeSelected()

    fun onStepByStepSelected()

}