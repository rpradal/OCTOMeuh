package com.octo.mob.octomeuh.settings.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.screen.SettingsScreen
import com.octo.mob.octomeuh.transversal.BasePresenterImpl

class SettingsPresenterImpl(val preferencesPersistor: PreferencesPersistor) : BasePresenterImpl<SettingsScreen>(), SettingsPresenter {


    override fun attach(screenToAttach: SettingsScreen?) {
        super.attach(screenToAttach)

        val repetitionModeDescriptionList = RepetitionModeDescription.values()
        val currentRepetitionMode = repetitionModeDescriptionList.find { it.repetitionMode == preferencesPersistor.getRepetitionMode() }
        currentRepetitionMode?.let {
            screen?.showCurrentRepetitionMode(currentRepetitionMode)
        }
    }

    override fun onDurationChangeRequest() {
        screen?.showDurationSelection()
    }

    override fun onRepetitionModeChangeRequest() {
        val repetitionModeDescriptionList = RepetitionModeDescription.values()
        val selectionIndex = repetitionModeDescriptionList.map { it.repetitionMode }.indexOf(preferencesPersistor.getRepetitionMode())
        screen?.showRepetitionModeSelection(repetitionModeDescriptionList, selectionIndex)
    }

    override fun onRepetitionModeSelected(repetitionModeDescription: RepetitionModeDescription) {
        preferencesPersistor.saveRepetitionMode(repetitionModeDescription.repetitionMode)
        screen?.showCurrentRepetitionMode(repetitionModeDescription)
    }

}