package com.octo.mob.octomeuh.transversal

interface HumanlyReadableDurationsConverter {

    fun getReadableStringFromValueInSeconds(secondsDuration: Int): String

}

abstract class HumanlyReadableDurationsConverterImpl : HumanlyReadableDurationsConverter {

    private companion object {
        val SECONDS_IN_ONE_MINUTE = 60
        val MINUTES_IN_ONE_HOUR = 60
        val SECONDS_IN_ONE_HOUR = MINUTES_IN_ONE_HOUR * SECONDS_IN_ONE_MINUTE
    }

    protected fun getSecondsValue(secondsDuration: Int) = secondsDuration % SECONDS_IN_ONE_MINUTE

    protected fun getMinutesValue(hourValue: Int, secondsDuration: Int) = (secondsDuration - hourValue * SECONDS_IN_ONE_HOUR) / MINUTES_IN_ONE_HOUR

    protected fun getHourValue(secondsDuration: Int) = secondsDuration / (SECONDS_IN_ONE_HOUR)

}

class CompactDurationConverterImpl : HumanlyReadableDurationsConverterImpl() {

    override fun getReadableStringFromValueInSeconds(secondsDuration: Int): String {
        val hourValue = getHourValue(secondsDuration)
        val minutesValue = getMinutesValue(hourValue, secondsDuration)
        val secondsValue = getSecondsValue(secondsDuration)

        val hourString = getCompactReadableHours(hourValue)
        val minutesString = getCompactReadableMinutes(hourValue, minutesValue)
        val secondsString = getCompactReadableSeconds(hourValue, minutesValue, secondsValue)

        return hourString + minutesString + secondsString
    }

    private fun getCompactReadableHours(hourValue: Int) = if (hourValue > 0) "$hourValue:" else ""

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

class ExpandedDurationConverterImpl : HumanlyReadableDurationsConverterImpl() {

    override fun getReadableStringFromValueInSeconds(secondsDuration: Int): String {
        val hourValue = getHourValue(secondsDuration)
        val minutesValue = getMinutesValue(hourValue, secondsDuration)
        val secondsValue = getSecondsValue(secondsDuration)

        val hourString = getReadableHours(hourValue)
        val minutesString = getReadableMinutes(minutesValue)
        val secondsString = getReadableSeconds(hourValue, minutesValue, secondsValue)

        return hourString + minutesString + secondsString
    }

    private fun getReadableSeconds(hourValue: Int, minutesValue: Int, secondsValue: Int) = if (secondsValue > 0) "${secondsValue}s" else if (hourValue == 0 && minutesValue == 0) "0s" else ""

    private fun getReadableMinutes(minutesValue: Int) = if (minutesValue > 0) "${minutesValue}min " else ""

    private fun getReadableHours(hourValue: Int) = if (hourValue > 0) "${hourValue}h " else ""

}
