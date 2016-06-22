package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import com.octo.mob.octomeuh.countdown.screen.RepetitionModeSelectorScreen
import com.octo.mob.octomeuh.transversal.BasePresenterImpl
import com.octo.mob.octomeuh.transversal.withNullable

class RepetitionModeSelectorPresenterImpl(val preferencesPersistor: PreferencesPersistor) : BasePresenterImpl<RepetitionModeSelectorScreen>(), RepetitionModeSelectorPresenter {

    override fun attach(screenToAttach: RepetitionModeSelectorScreen?) {
        super.attach(screenToAttach)

        val repetitionMode = preferencesPersistor.getRepetitionMode()

        when (repetitionMode) {
            RepetitionMode.LOOP -> {
                screen?.setLoopModeSelected(true)
                screen?.setStepByStepSelected(false)
            }
            RepetitionMode.STEP_BY_STEP -> {
                screen?.setLoopModeSelected(false)
                screen?.setStepByStepSelected(true)
            }
        }
    }

    override fun onLoopModeSelected() {
        preferencesPersistor.saveRepetitionMode(RepetitionMode.LOOP)

        withNullable(screen) {
            setLoopModeSelected(true)
            setStepByStepSelected(false)
            dismissScreen()
        }
    }

    override fun onStepByStepSelected() {
        preferencesPersistor.saveRepetitionMode(RepetitionMode.STEP_BY_STEP)

        withNullable(screen) {
            setLoopModeSelected(false)
            setStepByStepSelected(true)
            dismissScreen()
        }
    }
}