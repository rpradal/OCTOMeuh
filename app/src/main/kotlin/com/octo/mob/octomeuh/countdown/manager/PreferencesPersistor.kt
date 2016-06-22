package com.octo.mob.octomeuh.countdown.manager

import android.content.SharedPreferences
import com.octo.mob.octomeuh.countdown.model.CountDownValue
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import java.util.*

interface PreferencesPersistor {
    fun saveInitialDuration(countDownValue: CountDownValue)

    fun getInitialDuration(): CountDownValue

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
        internal val DEFAULT_COUNTDOWN_VALUE = CountDownValue.ONE_MINUTE
        internal val DEFAULT_REPETITION_MODE = RepetitionMode.STEP_BY_STEP
    }

    // ---------------------------------
    // INTERFACE IMPLEM
    // ---------------------------------

    override fun getInitialDuration(): CountDownValue {
        return extractEnumValue(CountDownValue.values(), INITIAL_DURATION_KEY, DEFAULT_COUNTDOWN_VALUE)
    }

    override fun saveInitialDuration(countDownValue: CountDownValue) {
        sharedPreferences.edit()
                .putString(INITIAL_DURATION_KEY, countDownValue.name)
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