package com.octo.mob.octomeuh.transversal.injection

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistorImpl
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import com.octo.mob.octomeuh.transversal.AnswersAnalyticsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesAppManager(firebaseAnalytics: FirebaseAnalytics) : AnalyticsManager = AnswersAnalyticsManager(application, firebaseAnalytics)

    @Provides
    @Singleton
    fun providesFirebaseAnalytics() : FirebaseAnalytics = FirebaseAnalytics.getInstance(application)

    @Provides
    @Singleton
    fun providesSharedPreferences() : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    fun providesPreferencesPersistor(sharedPreferences: SharedPreferences): PreferencesPersistor = PreferencesPersistorImpl(sharedPreferences)


}