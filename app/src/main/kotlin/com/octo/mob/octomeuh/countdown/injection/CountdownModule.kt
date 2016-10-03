package com.octo.mob.octomeuh.countdown.injection

import android.media.AudioManager
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.presenter.CountDownPresenter
import com.octo.mob.octomeuh.countdown.presenter.CountDownPresenterImpl
import com.octo.mob.octomeuh.countdown.presenter.UpsideDownPresenter
import com.octo.mob.octomeuh.countdown.presenter.UpsideDownPresenterImpl
import com.octo.mob.octomeuh.countdown.utils.AudioInformationProvider
import com.octo.mob.octomeuh.countdown.utils.AudioInformationProviderImpl
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import com.octo.mob.octomeuh.transversal.CompactDurationConverterImpl
import com.octo.mob.octomeuh.transversal.HumanlyReadableDurationsConverter
import dagger.Module
import dagger.Provides

@Module
class CountdownModule {

    @Provides
    @CountDownScope
    fun providesCountDownPresenter(analyticsManager: AnalyticsManager,
                                   preferencesPersistor: PreferencesPersistor,
                                   humanlyReadableDurationsConverter: HumanlyReadableDurationsConverter,
                                   audioInformationProvider: AudioInformationProvider): CountDownPresenter {
        return CountDownPresenterImpl(analyticsManager,
                humanlyReadableDurationsConverter,
                preferencesPersistor,
                audioInformationProvider)
    }

    @Provides
    @CountDownScope
    fun providesHumanelyReadableConverter() : HumanlyReadableDurationsConverter = CompactDurationConverterImpl()

    @Provides
    @CountDownScope
    fun providesUpsideDownPresenter(): UpsideDownPresenter = UpsideDownPresenterImpl()

    @Provides
    @CountDownScope
    fun providesAudioInformationProvider(audioManager: AudioManager) : AudioInformationProvider {
        return AudioInformationProviderImpl(audioManager)
    }

}