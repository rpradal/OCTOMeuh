package com.octo.mob.octomeuh.countdown.injection

import android.content.SharedPreferences
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistorImpl
import com.octo.mob.octomeuh.countdown.presenter.CountDownPresenter
import com.octo.mob.octomeuh.countdown.presenter.CountDownPresenterImpl
import com.octo.mob.octomeuh.countdown.presenter.UpsideDownPresenter
import com.octo.mob.octomeuh.countdown.presenter.UpsideDownPresenterImpl
import com.octo.mob.octomeuh.countdown.presenter.RepetitionModeSelectorPresenter
import com.octo.mob.octomeuh.countdown.presenter.RepetitionModeSelectorPresenterImpl
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverter
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverterImpl
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import dagger.Module
import dagger.Provides

@Module
class CountdownModule {

    @Provides
    @CountDownScope
    fun providesCountDownPresenter(analyticsManager: AnalyticsManager, preferencesPersistor: PreferencesPersistor): CountDownPresenter {
        return CountDownPresenterImpl(analyticsManager, preferencesPersistor)
    }

    @Provides
    @CountDownScope
    fun providesUpsideDownPresenter(): UpsideDownPresenter = UpsideDownPresenterImpl()

    @Provides
    @CountDownScope
    fun providesPreferencesPersistor(sharedPreferences: SharedPreferences): PreferencesPersistor = PreferencesPersistorImpl(sharedPreferences)

    @Provides
    @CountDownScope
    fun providesHumanDurationConverter(): HumanlyReadableDurationsConverter = HumanlyReadableDurationsConverterImpl()

    @Provides
    @CountDownScope
    fun providesRepetitionModePresenter(preferencesPersistor: PreferencesPersistor): RepetitionModeSelectorPresenter = RepetitionModeSelectorPresenterImpl(preferencesPersistor)
}