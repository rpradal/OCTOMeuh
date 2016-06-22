package com.octo.mob.octomeuh.countdown.utils

interface HumanlyReadableDurationsConverter {

    fun getReadableStringFromValueInSeconds(secondsDuration: Int): String

    fun getCompactReadableStringFromValueInSeconds(secondsDuration: Int): String

}

class HumanlyReadableDurationsConverterImpl : HumanlyReadableDurationsConverter {

    // ---------------------------------
    // CONSTANTS
    // ---------------------------------

    private companion object {
        val SECONDS_IN_ONE_MINUTE = 60
        val MINUTES_IN_ONE_HOUR = 60
    }

    // ---------------------------------
    // INTERFACE IMPLEMENTATION
    // ---------------------------------

    override fun getReadableStringFromValueInSeconds(secondsDuration: Int): String {
        val hourValue = getHourValue(secondsDuration)
        val minutesValue = getMinutesValue(hourValue, secondsDuration)
        val secondsValue = getSecondsValue(secondsDuration)

        val hourString = getReadableHours(hourValue)
        val minutesString = getReadableMinutes(minutesValue)
        val secondsString = getReadableSeconds(hourValue, minutesValue, secondsValue)

        return hourString + minutesString + secondsString
    }

    override fun getCompactReadableStringFromValueInSeconds(secondsDuration: Int): String {
        val hourValue = getHourValue(secondsDuration)
        val minutesValue = getMinutesValue(hourValue, secondsDuration)
        val secondsValue = getSecondsValue(secondsDuration)

        val hourString = getCompactReadableHours(hourValue)
        val minutesString = getCompactReadableMinutes(hourValue, minutesValue)
        val secondsString = getCompactReadableSeconds(hourValue, minutesValue, secondsValue)

        return hourString + minutesString + secondsString
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------

    private fun getCompactReadableHours(hourValue: Int) = if (hourValue > 0) "$hourValue:" else ""

    private fun getSecondsValue(secondsDuration: Int) = secondsDuration % SECONDS_IN_ONE_MINUTE

    private fun getMinutesValue(hourValue: Int, secondsDuration: Int) = (secondsDuration - hourValue * SECONDS_IN_ONE_MINUTE * MINUTES_IN_ONE_HOUR) / MINUTES_IN_ONE_HOUR

    private fun getHourValue(secondsDuration: Int) = secondsDuration / (SECONDS_IN_ONE_MINUTE * MINUTES_IN_ONE_HOUR)

    private fun getReadableSeconds(hourValue: Int, minutesValue: Int, secondsValue: Int) = if (secondsValue > 0) "${secondsValue}s" else if (hourValue == 0 && minutesValue == 0) "0s" else ""

    private fun getReadableMinutes(minutesValue: Int) = if (minutesValue > 0) "${minutesValue}min " else ""

    private fun getReadableHours(hourValue: Int) = if (hourValue > 0) "${hourValue}h " else ""

    private fun getCompactReadableMinutes(hourValue: Int, minutesValue: Int): String {
        if (minutesValue > 0) {
            return String.format("%02d:", minutesValue)
        } else if (hourValue > 0) {
            return "00:"
        } else {
            return ""
        }
    }

    private fun getCompactReadableSeconds(hourValue: Int, minutesValue: Int, secondsValue: Int): String {
        if (secondsValue > 0) {
            return String.format("%02d", secondsValue)
        } else if (hourValue == 0 && minutesValue == 0) {
            return "0"
        } else {
            return "00"
        }
    }

}
