package com.octo.mob.octomeuh.countdown.utils

import org.junit.Assert
import org.junit.Test

class HumanlyReadableDurationsConverterImplTest {

    val humanlyReadableDurationsConverter = HumanlyReadableDurationsConverterImpl()

    @Test
    fun testGetReadableStringFromValueInSeconds_WhenLessThanAMinute_ShouldDisplayOnlySeconds() {
        // Given
        val duration = 42

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("42s", niceDisplay)
    }

    @Test
    fun testGetReadableStringFromValueInSeconds_WhenLessThanAnHour_ShouldDisplaySecondsAndMinutes() {
        // Given
        val duration = 92

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("1min 32s", niceDisplay)
    }

    @Test
    fun testGetReadableStringFromValueInSeconds_WhenRoundNumberOfMinutes_ShouldDisplayMinutes() {
        // Given
        val duration = 60

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("1min ", niceDisplay)
    }

    @Test
    fun testGetReadableStringFromValueInSeconds_WhenMoreThanAnHour_ShouldDisplaySecondsMinutesAndHours() {
        // Given
        val duration = 10000

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2h 46min 40s", niceDisplay)
    }

    @Test
    fun testGetReadableStringFromValueInSeconds_WhenRoundNumberOfHours_ShouldDisplayHours() {
        // Given
        val duration = 7200

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2h ", niceDisplay)
    }

    @Test
    fun testGetCompactReadableStringFromValueInSeconds_WhenSecondsValueIsZero_ShouldNotReturnEmptyString() {
        // Given
        val duration = 0

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("0s", niceDisplay)
    }

    @Test
    fun testGetCompactReadableStringFromValueInSeconds_WhenLessThanAMinute_ShouldDisplayOnlySeconds() {
        // Given
        val duration = 42

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("42", niceDisplay)
    }

    @Test
    fun testGetCompactReadableStringFromValueInSeconds_WhenLessThanAnHour_ShouldDisplaySecondsAndMinutes() {
        // Given
        val duration = 92

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("01:32", niceDisplay)
    }

    @Test
    fun testGetCompactReadableStringFromValueInSeconds_WhenRoundNumberOfMinutes_ShouldDisplayMinutes() {
        // Given
        val duration = 60

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("01:00", niceDisplay)
    }

    @Test
    fun testGetCompactReadableStringFromValueInSeconds_WhenMoreThanAnHour_ShouldDisplaySecondsMinutesAndHours() {
        // Given
        val duration = 10000

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2:46:40", niceDisplay)
    }

    @Test
    fun testGetCompactReadableStringFromValueInSeconds_WhenRoundNumberOfHours_ShouldDisplayHours() {
        // Given
        val duration = 7200

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2:00:00", niceDisplay)
    }

    @Test
    fun testGetCompactReadableStringFromValueInSeconds_WhenSecondsValueIsZero_ShouldNotPadWithZero() {
        // Given
        val duration = 0

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("0", niceDisplay)
    }
}