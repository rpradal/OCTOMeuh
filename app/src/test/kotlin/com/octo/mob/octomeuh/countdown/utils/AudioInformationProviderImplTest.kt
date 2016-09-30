package com.octo.mob.octomeuh.countdown.utils

import android.media.AudioManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class AudioInformationProviderImplTest {

    @Mock
    lateinit var mockAudioManager : AudioManager

    lateinit var audioInformationProviderImpl : AudioInformationProviderImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        audioInformationProviderImpl = AudioInformationProviderImpl(mockAudioManager)
    }

    @Test
    fun testIsSoundMuted_WhenAudioVolumeIsZero_ShouldReturnTrue() {
        // Given
        Mockito.`when`(mockAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)).thenReturn(0)

        // When
        val isMuted = audioInformationProviderImpl.isSoundMuted()

        // Then
        Assert.assertTrue(isMuted)
    }

    @Test
    fun testIsSoundMuted_WhenAudioVolumeIsTwo_ShouldReturnFalse() {
        // Given
        Mockito.`when`(mockAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)).thenReturn(1)

        // When
        val isMuted = audioInformationProviderImpl.isSoundMuted()

        // Then
        Assert.assertFalse(isMuted)
    }

}