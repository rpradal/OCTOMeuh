package com.octo.mob.octomeuh.settings.presenter

import com.octo.mob.octomeuh.settings.screen.DurationPickerScreen
import com.octo.mob.octomeuh.transversal.BasePresenter

interface DurationPickerPresenter : BasePresenter<DurationPickerScreen> {
    fun onDurationSelected(durationInSeconds: Int)
}