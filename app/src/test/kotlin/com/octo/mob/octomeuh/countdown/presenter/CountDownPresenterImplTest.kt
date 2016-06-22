package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.model.CountDownValue
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import com.octo.mob.octomeuh.countdown.screen.CountDownScreen
import com.octo.mob.octomeuh.countdown.utils.SecondCountDownTimer
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class CountDownPresenterImplTest() {

    lateinit var mockCountDownScreen: CountDownScreen
    lateinit var mockAnalyticsManager: AnalyticsManager
    lateinit var mockPreferencesPersistor : PreferencesPersistor
    lateinit var countDownPresenter: CountDownPresenterImpl

    @Before
    fun setUp() {
        mockCountDownScreen = mock(CountDownScreen::class.java)
        mockAnalyticsManager = mock(AnalyticsManager::class.java)
        mockPreferencesPersistor = mock(PreferencesPersistor::class.java)
        countDownPresenter = CountDownPresenterImpl(mockAnalyticsManager, mockPreferencesPersistor)
    }

    @Test
    fun testConstructor_InitialCountdownValueShouldBeTheOneProvidedByPreferencesPersistor() {
        // Given
        `when`(mockPreferencesPersistor.getInitialDuration()).thenReturn(CountDownValue.ONE_MINUTE_THIRTY_SECONDS)

        // When
        val customCountDownPresenter = CountDownPresenterImpl(mockAnalyticsManager, mockPreferencesPersistor)

        // Then
        Assert.assertEquals(CountDownValue.ONE_MINUTE_THIRTY_SECONDS, customCountDownPresenter.initialCountDownValue)
    }

    @Test
    fun testStartTimer() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        spyCountDownPresenter.initialCountDownValue = CountDownValue.FORTYFIVE_SECONDS
        doNothing().`when`(spyCountDownPresenter).restartTimer()
        spyCountDownPresenter.screen = mockCountDownScreen
        spyCountDownPresenter.speakerCounter = 16

        // When
        spyCountDownPresenter.startTimer()

        // Then
        verify(mockCountDownScreen).setTimerVisibility(true)
        verify(mockCountDownScreen).setStopAvailability(true)
        verify(mockCountDownScreen).setTimerValue(45)
        verify(mockCountDownScreen).setNextAttendeeVisibility(true)
        verify(mockCountDownScreen).setStartVisibility(false)
        verify(mockCountDownScreen).startChronometer()
        verify(mockCountDownScreen).setFooterVisibility(true)
        verify(mockCountDownScreen).keepAwake(true)
        verify(mockCountDownScreen).setSpeakerCounter(16)
        verify(spyCountDownPresenter).restartTimer()
        Mockito.verifyNoMoreInteractions(mockCountDownScreen)

    }

    @Test
    fun testCancelMeeting() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        val mockCountDown = mock(SecondCountDownTimer::class.java)

        spyCountDownPresenter.screen = mockCountDownScreen
        spyCountDownPresenter.countDownTimer = mockCountDown

        // When
        spyCountDownPresenter.onCancelMeeting()

        // Then
        verify(mockCountDownScreen).setTimerVisibility(false)
        verify(mockCountDownScreen).setStopAvailability(false)
        verify(mockCountDownScreen).setNextAttendeeVisibility(false)
        verify(mockCountDownScreen).setStartVisibility(true)
        verify(mockCountDownScreen).setFooterVisibility(false)
        verify(mockCountDownScreen).keepAwake(false)
        verify(mockCountDown).cancel()
        Assert.assertEquals(0, spyCountDownPresenter.speakerCounter)
        Mockito.verifyNoMoreInteractions(mockCountDownScreen)

    }

    @Test
    fun testRestartTimer_WhenCountDownTimerIsNotExisting_ShouldInstantiateNewCountDown() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        val mockCountDown = mock(SecondCountDownTimer::class.java)
        spyCountDownPresenter.screen = mockCountDownScreen
        doReturn(mockCountDown).`when`(spyCountDownPresenter).generateNewSecondsTimer()

        // When
        spyCountDownPresenter.restartTimer()

        // Then
        verify(mockCountDownScreen).switchTimerToNormalMode()
        verify(spyCountDownPresenter).generateNewSecondsTimer()
        Assert.assertEquals(mockCountDown, spyCountDownPresenter.countDownTimer)
        verify(mockCountDown).start()
    }

    @Test
    fun testRestartTimer_WhenCountDownTimerIsExisting_ShouldCancelCountDownAndRelaunchANewOne() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        val mockOldCountDown = mock(SecondCountDownTimer::class.java)
        val mockNewCountDown = mock(SecondCountDownTimer::class.java)
        spyCountDownPresenter.screen = mockCountDownScreen
        spyCountDownPresenter.countDownTimer = mockOldCountDown
        doReturn(mockNewCountDown).`when`(spyCountDownPresenter).generateNewSecondsTimer()

        // When
        spyCountDownPresenter.restartTimer()

        // Then
        verify(mockOldCountDown).cancel()
        verify(mockCountDownScreen).switchTimerToNormalMode()
        verify(spyCountDownPresenter).generateNewSecondsTimer()
        Assert.assertEquals(mockNewCountDown, spyCountDownPresenter.countDownTimer)
        verify(mockNewCountDown).start()
    }

    @Test
    fun testIncreaseSpeakerCounter_ShouldIncreaseTotalSpeakerCountOfOne() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        spyCountDownPresenter.screen = mockCountDownScreen
        spyCountDownPresenter.speakerCounter = 42

        // When
        spyCountDownPresenter.increaseSpeakerCounter()

        // Then
        Assert.assertEquals(43, spyCountDownPresenter.speakerCounter)
        verify(mockCountDownScreen).setSpeakerCounter(43)
    }

    @Test
    fun testOnCountDownOver_WhenRepetModeIsLoop_ShouldNotifyTimerEndAndRestartTimer() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        spyCountDownPresenter.screen = mockCountDownScreen
        doNothing().`when`(spyCountDownPresenter).onNewSpeakerCountDown()
        `when`(mockPreferencesPersistor.getRepetitionMode()).thenReturn(RepetitionMode.LOOP)

        // When
        spyCountDownPresenter.onCountDownOver()

        // Then
        verify(mockCountDownScreen).notifyTimerFinished()
        verify(spyCountDownPresenter).onNewSpeakerCountDown()
        verifyNoMoreInteractions(mockCountDownScreen)
    }

    @Test
    fun testOnCountDownOver_WhenRepetModeIsStepByStep_ShouldNotifyTimerEndAndSwitchToFinishedMode() {
        // Given
        countDownPresenter.screen = mockCountDownScreen
        `when`(mockPreferencesPersistor.getRepetitionMode()).thenReturn(RepetitionMode.STEP_BY_STEP)

        // When
        countDownPresenter.onCountDownOver()

        // Then
        verify(mockCountDownScreen).notifyTimerFinished()
        verify(mockCountDownScreen).switchTimerToFinishedMode()
        verifyNoMoreInteractions(mockCountDownScreen)
    }

    @Test
    fun testOnValueUpdated() {
        // Given
        countDownPresenter.screen = mockCountDownScreen

        // When
        countDownPresenter.onValueUpdated(42)

        // Then
        verify(mockCountDownScreen).setTimerValue(42)
        verifyNoMoreInteractions(mockCountDownScreen)
    }

    @Test
    fun testOnNewSpeakerCountDown_ShouldRestartTimerAndIncreaseCounter() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        doNothing().`when`(spyCountDownPresenter).restartTimer()
        doNothing().`when`(spyCountDownPresenter).increaseSpeakerCounter()

        // When
        spyCountDownPresenter.onNewSpeakerCountDown()

        // Then
        verify(spyCountDownPresenter).onNewSpeakerCountDown()
        verify(spyCountDownPresenter).increaseSpeakerCounter()
        verify(spyCountDownPresenter).restartTimer()
        verifyNoMoreInteractions(spyCountDownPresenter)
    }

    @Test
    fun testOnActionFeedbackClicked() {
        // Given
        countDownPresenter.screen = mockCountDownScreen

        // When
        countDownPresenter.onActionFeedbackClicked()

        // Then
        verify(mockCountDownScreen).sendFeedbackEmailAction()
        verify(mockAnalyticsManager).logSendFeedback()
    }

    @Test
    fun testSetInitialCountDownValue() {
        // Given
        countDownPresenter.screen = mockCountDownScreen
        val countDownValue = CountDownValue.THIRTY_SECONDS

        // When
        countDownPresenter.setInitialCountDownValue(countDownValue)

        // Then
        verify(mockPreferencesPersistor).saveInitialDuration(countDownValue)
        verify(mockAnalyticsManager).logChangeCountDownDuration(countDownValue.durationInSeconds)
    }

    @Test
    fun testGetInitialCountDownValue() {
        // Given
        countDownPresenter.screen = mockCountDownScreen
        val countDownValue = CountDownValue.THIRTY_SECONDS
        countDownPresenter.setInitialCountDownValue(countDownValue)

        // When
        val countDownValueInSeconds = countDownPresenter.getInitialCountDownValue()

        // Then
        Assert.assertEquals(CountDownValue.THIRTY_SECONDS, countDownValueInSeconds)
    }
}
