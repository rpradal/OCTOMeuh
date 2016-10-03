package com.octo.mob.octomeuh.transversal

import org.junit.Assert
import org.junit.Test

@Suppress("IllegalIdentifier")
class CompactDurationsConverterImplTest {

    val humanlyReadableDurationsConverter = CompactDurationConverterImpl()

    @Test
    fun `When less than a minute we should display only seconds in a compact way`() {
        // Given
        val duration = 42

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("42", niceDisplay)
    }

    @Test
    fun `When less than an hour we should display minutes and seconds in a compact way`() {
        // Given
        val duration = 92

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("01:32", niceDisplay)
    }

    @Test
    fun `When round number of minutes we should display 00 seconds`() {
        // Given
        val duration = 60

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("01:00", niceDisplay)
    }

    @Test
    fun `When more than an hour we should display hours minutes and seconds in a compact way`() {
        // Given
        val duration = 10000

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2:46:40", niceDisplay)
    }

    @Test
    fun `When round number of hour we should display 00 minutes and 00 seconds`() {
        // Given
        val duration = 7200

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("2:00:00", niceDisplay)
    }

    @Test
    fun `When timer is 0 we should display plain 0 with no padding`() {
        // Given
        val duration = 0

        // When
        val niceDisplay = humanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(duration)

        // Then
        Assert.assertEquals("0", niceDisplay)
    }
}