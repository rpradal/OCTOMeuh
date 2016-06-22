package com.octo.mob.octomeuh.countdown.utils

import android.os.CountDownTimer


interface CountDownListener {
    fun onValueUpdated(countValueInSeconds: Long)

    fun onCountDownOver()
}

open class SecondCountDownTimer(val secondsToCount: Int, val countDownListener: CountDownListener) : CountDownTimer((secondsToCount * 1000).toLong(), 1000) {
    override fun onFinish() {
        countDownListener.onValueUpdated(0)
        countDownListener.onCountDownOver()
    }

    override fun onTick(millisUntilFinished: Long) {
        countDownListener.onValueUpdated(millisUntilFinished / 1000)
    }
}