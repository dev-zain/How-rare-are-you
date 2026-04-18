package com.devzain.howrareareyou.utils

import android.content.Context
import android.media.MediaPlayer
import com.devzain.howrareareyou.R

object SoundManager {
    private var clickPlayer: MediaPlayer? = null
    private var successPlayer: MediaPlayer? = null

    fun playClick(context: Context) {
        try {
            if (clickPlayer == null) {
                clickPlayer = MediaPlayer.create(context.applicationContext, R.raw.click)
            }
            if (clickPlayer?.isPlaying == true) {
                clickPlayer?.seekTo(0)
            }
            clickPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playSuccess(context: Context) {
        try {
            if (successPlayer == null) {
                successPlayer = MediaPlayer.create(context.applicationContext, R.raw.success)
            }
            if (successPlayer?.isPlaying == true) {
                successPlayer?.seekTo(0)
            }
            successPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
