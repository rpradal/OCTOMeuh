package com.octo.mob.octomeuh.countdown.manager

import android.content.SharedPreferences
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import java.util.*

interface PreferencesPersistor {
    fun saveInitialDuration(initialDurationSeconds: Int)

    fun getInitialDuration(): Int

    fun getRepetitionMode(): RepetitionMode

    fun saveRepetitionMode(repetitionMode: RepetitionMode)
}

class PreferencesPersistorImpl(val sharedPreferences: SharedPreferences) : PreferencesPersistor {

    // ---------------------------------
    // CONSTANTS
    // ---------------------------------

    companion object {
        internal val INITIAL_DURATION_KEY = "INITIAL_DURATION_KEY"
        internal val REPETITION_MODE_KEY = "REPETITION_MODE_KEY"
        internal val DEFAULT_COUNTDOWN_DURATION_SECONDS = 60
        internal val DEFAULT_REPETITION_MODE = RepetitionMode.STEP_BY_STEP
    }

    // ---------------------------------
    // INTERFACE IMPLEM
    // ---------------------------------

    override fun getInitialDuration(): Int {
        try {
            return sharedPreferences.getInt(INITIAL_DURATION_KEY, DEFAULT_COUNTDOWN_DURATION_SECONDS)
        } catch (exception: ClassCastException) {
            return DEFAULT_COUNTDOWN_DURATION_SECONDS
        }
    }

    override fun saveInitialDuration(initialDurationSeconds: Int) {
        sharedPreferences.edit()
                .putInt(INITIAL_DURATION_KEY, initialDurationSeconds)
                .apply()
    }

    override fun getRepetitionMode(): RepetitionMode {
        return extractEnumValue(RepetitionMode.values(), REPETITION_MODE_KEY, DEFAULT_REPETITION_MODE)
    }

    override fun saveRepetitionMode(repetitionMode: RepetitionMode) {
        sharedPreferences.edit()
                .putString(REPETITION_MODE_KEY, repetitionMode.name)
                .apply()
    }

    // ---------------------------------
    // PRIVATE METHOD
    // ---------------------------------

    private fun <T : kotlin.Enum<T>> extractEnumValue(valueLists: Array<T>, key: String, defaultValue: T): T {

        val extractedValue: T

        try {
            extractedValue = valueLists.first { it.name == sharedPreferences.getString(key, defaultValue.name) }
        } catch (exception: NoSuchElementException) {
            extractedValue = defaultValue
        }

        return extractedValue
    }
}