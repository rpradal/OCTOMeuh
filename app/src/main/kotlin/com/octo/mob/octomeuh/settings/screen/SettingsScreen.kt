package com.octo.mob.octomeuh.settings.screen

import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenterImpl

interface SettingsScreen {
    fun showRepetitionModeSelection(values: Array<RepetitionModeDescription>, selectionIndex: Int)

    fun showDurationSelection()

    fun showCurrentRepetitionMode(repetitionModeDescription: RepetitionModeDescription)

    fun showCurrentDuration(initialDuration: String)

    fun showVersionNumber(appVersionLabel: String)

}