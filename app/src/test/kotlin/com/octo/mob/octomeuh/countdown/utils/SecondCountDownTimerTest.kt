package com.octo.mob.octomeuh.countdown.utils

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("IllegalIdentifier")
class SecondCountDownTimerTest {

    @Mock
    lateinit var countDownListener : CountDownListener

    lateinit var secondCountDownTimer: SecondCountDownTimer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        secondCountDownTimer = SecondCountDownTimer(10, countDownListener)
    }

    @Test
    fun `When countdown is over we should notify the new value and trigger countdown end`() {
        // Given

        // When
        secondCountDownTimer.onFinish()

        // Then
        Mockito.verify(countDownListener).onCountDownOver()
        Mockito.verify(countDownListener).onValueUpdated(0)
    }

    @Test
    fun `When countdown is ticking we should update with a rounded value`() {
        // Given

        // When
        secondCountDownTimer.onTick(1243)

        // Then
        Mockito.verify(countDownListener).onValueUpdated(1)

    }

}