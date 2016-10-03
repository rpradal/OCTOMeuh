package com.octo.mob.octomeuh.countdown.utils

import android.media.AudioManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("IllegalIdentifier")
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
    fun `When audio volume is zero then audi is muted`() {
        // Given
        Mockito.`when`(mockAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)).thenReturn(0)

        // When
        val isMuted = audioInformationProviderImpl.isSoundMuted()

        // Then
        Assert.assertTrue(isMuted)
    }

    @Test
    fun `When audio volume is two then audio is not muted`() {
        // Given
        Mockito.`when`(mockAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)).thenReturn(1)

        // When
        val isMuted = audioInformationProviderImpl.isSoundMuted()

        // Then
        Assert.assertFalse(isMuted)
    }

}