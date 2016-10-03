package com.octo.mob.octomeuh.countdown.utils

import org.junit.Assert
import org.junit.Test

@Suppress("IllegalIdentifier")
class HumanlyReadableDurationsConverterImplTest {

    val humanlyReadableDurationsConverter = HumanlyReadableDurationsConverterImpl()

    @Test
    fun `When duration is below one minute we should display only seconds`() {
        // Given
        val duration = 42

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("42s", niceDisplay)
    }

    @Test
    fun `When duration is less than an hout we should display minutes and seconds`() {
        // Given
        val duration = 92

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("1min 32s", niceDisplay)
    }

    @Test
    fun `When there is a round number of minutes we should not display seconds`() {
        // Given
        val duration = 60

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("1min ", niceDisplay)
    }

    @Test
    fun `When more than one hour we should display hours minutes and seconds`() {
        // Given
        val duration = 10000

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2h 46min 40s", niceDisplay)
    }

    @Test
    fun `When there is a round number of hour we should not display minutes and seconds`() {
        // Given
        val duration = 7200

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2h ", niceDisplay)
    }

    @Test
    fun `When 0 seconds are remaining we should display 0s`() {
        // Given
        val duration = 0

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("0s", niceDisplay)
    }

    @Test
    fun `When less than a minute we should display only seconds in a compact way`() {
        // Given
        val duration = 42

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("42", niceDisplay)
    }

    @Test
    fun `When less than an hour we should display minutes and seconds in a compact way`() {
        // Given
        val duration = 92

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("01:32", niceDisplay)
    }

    @Test
    fun `When round number of minutes we should display 00 seconds`() {
        // Given
        val duration = 60

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("01:00", niceDisplay)
    }

    @Test
    fun `When more than an hour we should display hours minutes and seconds in a compact way`() {
        // Given
        val duration = 10000

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2:46:40", niceDisplay)
    }

    @Test
    fun `When round number of hour we should display 00 minutes and 00 seconds`() {
        // Given
        val duration = 7200

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2:00:00", niceDisplay)
    }

    @Test
    fun `When timer is 0 we should display plain 0 with no padding`() {
        // Given
        val duration = 0

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getCompactReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("0", niceDisplay)
    }
}