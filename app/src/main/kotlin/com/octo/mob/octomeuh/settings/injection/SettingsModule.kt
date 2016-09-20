package com.octo.mob.octomeuh.settings.injection

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenter
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun providesSettingsPresenter(preferencesPersistor: PreferencesPersistor): SettingsPresenter = SettingsPresenterImpl(preferencesPersistor)
}