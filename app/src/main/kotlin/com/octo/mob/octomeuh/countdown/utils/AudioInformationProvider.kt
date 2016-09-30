package com.octo.mob.octomeuh.countdown.utils

import android.media.AudioManager

interface AudioInformationProvider {
    fun isSoundMuted() : Boolean
}

class AudioInformationProviderImpl(val audioManager: AudioManager) : AudioInformationProvider {

    override fun isSoundMuted(): Boolean {
        val streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        return streamVolume <= 0
    }

}