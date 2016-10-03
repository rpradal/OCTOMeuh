package com.octo.mob.octomeuh.settings.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.screen.SettingsScreen
import com.octo.mob.octomeuh.settings.utils.AppInformation
import com.octo.mob.octomeuh.transversal.BasePresenterImpl
import com.octo.mob.octomeuh.transversal.HumanlyReadableDurationsConverter

class SettingsPresenterImpl(val preferencesPersistor: PreferencesPersistor,
                            val humanlyReadableDurationsConverter: HumanlyReadableDurationsConverter,
                            val appInformation: AppInformation) : BasePresenterImpl<SettingsScreen>(), SettingsPresenter {


    override fun attach(screenToAttach: SettingsScreen?) {
        super.attach(screenToAttach)

        val repetitionModeDescriptionList = RepetitionModeDescription.values()
        val currentRepetitionMode = repetitionModeDescriptionList.find { it.repetitionMode == preferencesPersistor.getRepetitionMode() }
        currentRepetitionMode?.let {
            screen?.showCurrentRepetitionMode(currentRepetitionMode)
        }

        screen?.showCurrentDuration(getFormattedInitialDuration())
        screen?.showVersionNumber(appInformation.getAppVersionLabel())
    }

    override fun onDurationChangeRequest() {
        screen?.showDurationSelection()
    }

    override fun onRepetitionModeChangeRequest() {
        val repetitionModeDescriptionList = RepetitionModeDescription.values()
        val repetitionMode = preferencesPersistor.getRepetitionMode()
        val selectionIndex = repetitionModeDescriptionList
                .map { it.repetitionMode }
                .indexOf(repetitionMode)
        screen?.showRepetitionModeSelection(repetitionModeDescriptionList, selectionIndex)
    }

    override fun onRepetitionModeSelected(repetitionModeDescription: RepetitionModeDescription) {
        preferencesPersistor.saveRepetitionMode(repetitionModeDescription.repetitionMode)
        screen?.showCurrentRepetitionMode(repetitionModeDescription)
    }

    override fun onDurationChanged() {
        screen?.showCurrentDuration(getFormattedInitialDuration())
    }

    private fun getFormattedInitialDuration(): String {
        val initialDuration = preferencesPersistor.getInitialDuration()
        return humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(initialDuration)
    }
}