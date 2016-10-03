package com.octo.mob.octomeuh.countdown.manager

import android.content.SharedPreferences
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mockito
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class PreferencesPersistorImplTest{

    lateinit var mockSharedPreferences : SharedPreferences
    lateinit var preferencesPersistor : PreferencesPersistorImpl

    @Before
    fun setUp() {
        mockSharedPreferences = Mockito.mock(SharedPreferences::class.java)
        preferencesPersistor = PreferencesPersistorImpl(mockSharedPreferences)
    }

    @Test
    fun `When stored initial duration is invalid then the default duration should be returned`() {
        // Given
        `when`(mockSharedPreferences.getInt(eq(PreferencesPersistorImpl.INITIAL_DURATION_KEY), Matchers.anyInt())).thenThrow(ClassCastException::class.java)

        // When
        val initialDuration = preferencesPersistor.getInitialDuration()

        // Then
        Assert.assertEquals(PreferencesPersistorImpl.DEFAULT_COUNTDOWN_DURATION_SECONDS, initialDuration)
    }

    @Test
    fun `When the stored intial duration is valid then preference persistore should return it`() {
        // Given
        `when`(mockSharedPreferences.getInt(eq(PreferencesPersistorImpl.INITIAL_DURATION_KEY), Matchers.anyInt())).thenReturn(70)

        // When
        val initialDuration = preferencesPersistor.getInitialDuration()

        // Then
        Assert.assertEquals(70, initialDuration)
    }

    @Test
    fun `Persisted initial duration should be properly written in shared preferences`() {
        // Given
        val editor = Mockito.mock(SharedPreferences.Editor::class.java)
        `when`(mockSharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putInt(anyString(), Matchers.anyInt())).thenReturn(editor)

        // When
        preferencesPersistor.saveInitialDuration(62)

        // Then
        verify(editor).putInt(eq(PreferencesPersistorImpl.INITIAL_DURATION_KEY), eq(62))
        verify(editor).apply()
        verifyNoMoreInteractions(editor)

    }

    @Test
    fun `Repetition mode should be properly written in shared preferences`() {
        // Given
        val editor = Mockito.mock(SharedPreferences.Editor::class.java)
        `when`(mockSharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)

        // When
        preferencesPersistor.saveRepetitionMode(RepetitionMode.LOOP)

        // Then
        verify(editor).putString(eq(PreferencesPersistorImpl.REPETITION_MODE_KEY), eq(RepetitionMode.LOOP.name))
        verify(editor).apply()
        verifyNoMoreInteractions(editor)

    }

    @Test
    fun `When the stored repetition mode cannot be read the default mode should be returned`() {
        // Given
        `when`(mockSharedPreferences.getString(eq(PreferencesPersistorImpl.REPETITION_MODE_KEY), Matchers.anyString())).thenReturn("foo key")

        // When
        val initialDuration = preferencesPersistor.getRepetitionMode()

        // Then
        Assert.assertEquals(PreferencesPersistorImpl.DEFAULT_REPETITION_MODE, initialDuration)
    }

    @Test
    fun `When the stored repetition mode can be read then it should be returned`() {
        // Given
        `when`(mockSharedPreferences.getString(eq(PreferencesPersistorImpl.REPETITION_MODE_KEY), Matchers.anyString())).thenReturn("LOOP")

        // When
        val initialDuration = preferencesPersistor.getRepetitionMode()

        // Then
        Assert.assertEquals(RepetitionMode.LOOP, initialDuration)
    }
}