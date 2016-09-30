package com.octo.mob.octomeuh.settings.injection

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverter
import com.octo.mob.octomeuh.settings.presenter.DurationPickerPresenter
import com.octo.mob.octomeuh.settings.presenter.DurationPickerPresenterImpl
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenter
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenterImpl
import com.octo.mob.octomeuh.settings.utils.AppInformation
import com.octo.mob.octomeuh.settings.utils.AppInformationImpl
import com.octo.mob.octomeuh.settings.utils.RepetitionModeDialogCreator
import com.octo.mob.octomeuh.settings.utils.RepetitionModeDialogCreatorImpl
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun providesSettingsPresenter(preferencesPersistor: PreferencesPersistor,
                                  humanlyReadableDurationsConverter: HumanlyReadableDurationsConverter,
                                  appVersionInfo: AppInformation): SettingsPresenter {
        return SettingsPresenterImpl(preferencesPersistor, humanlyReadableDurationsConverter, appVersionInfo)
    }

    @Provides
    @SettingsScope
    fun providesAppInformation(): AppInformation = AppInformationImpl()

    @Provides
    @SettingsScope
    fun providesRepetitionModeDialogCreator(settingsPresenter: SettingsPresenter): RepetitionModeDialogCreator{
        return RepetitionModeDialogCreatorImpl(settingsPresenter)
    }

    @Provides
    @SettingsScope
    fun providesDurationPickerPresenter(preferencesPersistor: PreferencesPersistor): DurationPickerPresenter = DurationPickerPresenterImpl(preferencesPersistor)
}