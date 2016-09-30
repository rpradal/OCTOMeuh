package com.octo.mob.octomeuh.settings.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.settings.screen.DurationPickerScreen
import com.octo.mob.octomeuh.transversal.BasePresenterImpl

class DurationPickerPresenterImpl(val preferencesPersistor: PreferencesPersistor) : DurationPickerPresenter, BasePresenterImpl<DurationPickerScreen>() {
    override fun onDurationSelected(durationInSeconds: Int) {
        preferencesPersistor.saveInitialDuration(durationInSeconds)
        screen?.closeView()
    }

    override fun attach(screenToAttach: DurationPickerScreen?) {
        super.attach(screenToAttach)
        screen?.setDuration(preferencesPersistor.getInitialDuration())
    }
}