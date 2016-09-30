package com.octo.mob.octomeuh.settings.presenter

import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.screen.SettingsScreen
import com.octo.mob.octomeuh.transversal.BasePresenter

interface SettingsPresenter : BasePresenter<SettingsScreen> {
    fun onDurationChangeRequest()

    fun onRepetitionModeChangeRequest()

    fun onRepetitionModeSelected(repetitionModeDescription: RepetitionModeDescription)

    fun onDurationChanged()
}

