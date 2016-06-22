package com.octo.mob.octomeuh.countdown.manager

import android.content.SharedPreferences
import com.octo.mob.octomeuh.countdown.model.CountDownValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class PreferencesPersistorImplTest{

    lateinit var mockSharedPreferences : SharedPreferences
    lateinit var preferencesPersistor : PreferencesPersistorImpl

    @Before
    fun setUp() {
        mockSharedPreferences = Mockito.mock(SharedPreferences::class.java)
        preferencesPersistor = PreferencesPersistorImpl(mockSharedPreferences)
    }

    @Test
    fun testGetInitialDuration_WhenStoredValueIsInvalid_ShouldReturnDefaultValue() {
        // Given
        `when`(mockSharedPreferences.getString(eq(PreferencesPersistorImpl.INITIAL_DURATION_KEY), anyString())).thenReturn("Invalid key")

        // When
        val initialDuration = preferencesPersistor.getInitialDuration()

        // Then
        Assert.assertEquals(PreferencesPersistorImpl.DEFAULT_COUNTDOWN_VALUE, initialDuration)
    }

    @Test
    fun testGetInitialDuration_WhenStoredKeyIsValid_ShouldReturnCorrespondingValue() {
        // Given
        `when`(mockSharedPreferences.getString(eq(PreferencesPersistorImpl.INITIAL_DURATION_KEY), anyString())).thenReturn("TWO_MINUTES")

        // When
        val initialDuration = preferencesPersistor.getInitialDuration()

        // Then
        Assert.assertEquals(CountDownValue.TWO_MINUTES, initialDuration)
    }

    @Test
    fun testSaveInitialDuration() {
        // Given
        val editor = Mockito.mock(SharedPreferences.Editor::class.java)
        `when`(mockSharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)

        // When
        preferencesPersistor.saveInitialDuration(CountDownValue.ONE_MINUTE_THIRTY_SECONDS)

        // Then
        verify(editor).putString(eq(PreferencesPersistorImpl.INITIAL_DURATION_KEY), eq(CountDownValue.ONE_MINUTE_THIRTY_SECONDS.name))
        verify(editor).apply()
        verifyNoMoreInteractions(editor)

    }
}