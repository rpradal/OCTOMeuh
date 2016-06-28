package com.octo.mob.octomeuh.countdown.utils

import android.os.CountDownTimer


interface CountDownListener {
    fun onValueUpdated(countValueInSeconds: Long)

    fun onCountDownOver()
}

open class SecondCountDownTimer(secondsToCount: Int, val countDownListener: CountDownListener) :
                 CountDownTimer(secondsToCount.secondsToMillis(), NOTIFICATION_INTERVAL_IN_MILLIS) {
    
    companion object {
        private val NOTIFICATION_INTERVAL_IN_MILLIS = 500L
    }
    
    override fun onFinish() {
        countDownListener.onValueUpdated(0)
        countDownListener.onCountDownOver()
    }

    override fun onTick(millisUntilFinished: Long) {
        countDownListener.onValueUpdated(millisUntilFinished / 1000)
    }
    

}

private fun Int.secondsToMillis() : Long {
    return this * 1000L
}