package com.example.util

import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log

class SoundAndFeedbackManager {
    private var toneGenerator: ToneGenerator? = null

    init {
        runCatching {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 65)
        }.onFailure {
            Log.w("SoundManager", "ToneGenerator unavailable", it)
        }
    }

    fun playTap(isMuted: Boolean) {
        if (isMuted) return
        runCatching {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 45)
        }
    }

    fun playCorrect(isMuted: Boolean) {
        if (isMuted) return
        runCatching {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 150)
        }
    }

    fun playWrongGentle(isMuted: Boolean) {
        if (isMuted) return
        runCatching {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 110)
        }
    }

    fun playBadgeEarned(isMuted: Boolean) {
        if (isMuted) return
        runCatching {
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_CONFIRM, 240)
        }
    }

    fun release() {
        runCatching { toneGenerator?.release() }
        toneGenerator = null
    }
}
