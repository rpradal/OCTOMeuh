package com.octo.mob.octomeuh.transversal.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistorImpl
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverter
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverterImpl
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import com.octo.mob.octomeuh.transversal.AnswersAnalyticsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesAppManager(firebaseAnalytics: FirebaseAnalytics): AnalyticsManager = AnswersAnalyticsManager(application, firebaseAnalytics)

    @Provides
    @Singleton
    fun providesFirebaseAnalytics(): FirebaseAnalytics = FirebaseAnalytics.getInstance(application)

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    fun providesPreferencesPersistor(sharedPreferences: SharedPreferences): PreferencesPersistor = PreferencesPersistorImpl(sharedPreferences)

    @Provides
    @Singleton
    fun providesHumanDurationConverter(): HumanlyReadableDurationsConverter = HumanlyReadableDurationsConverterImpl()

    @Provides
    fun providesAudioManager(): AudioManager = application.getSystemService(Context.AUDIO_SERVICE) as AudioManager
}