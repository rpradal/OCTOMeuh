package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import com.octo.mob.octomeuh.countdown.screen.CountDownScreen
import com.octo.mob.octomeuh.countdown.utils.AudioInformationProvider
import com.octo.mob.octomeuh.countdown.utils.SecondCountDownTimer
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import com.octo.mob.octomeuh.transversal.HumanlyReadableDurationsConverter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

@Suppress("IllegalIdentifier")
class CountDownPresenterImplTest() {

    lateinit var mockCountDownScreen: CountDownScreen
    lateinit var mockAnalyticsManager: AnalyticsManager
    lateinit var mockPreferencesPersistor : PreferencesPersistor
    lateinit var mockHumanlyReadableDurationsConverter: HumanlyReadableDurationsConverter
    lateinit var mockAudioInformationProvider: AudioInformationProvider
    lateinit var countDownPresenter: CountDownPresenterImpl

    @Before
    fun setUp() {
        mockCountDownScreen = mock(CountDownScreen::class.java)
        mockAnalyticsManager = mock(AnalyticsManager::class.java)
        mockPreferencesPersistor = mock(PreferencesPersistor::class.java)
        mockHumanlyReadableDurationsConverter = mock(HumanlyReadableDurationsConverter::class.java)
        mockAudioInformationProvider = mock(AudioInformationProvider::class.java)

        Mockito.`when`(mockHumanlyReadableDurationsConverter.getReadableStringFromValueInSeconds(Mockito.anyInt())).thenReturn("42s")

        countDownPresenter = CountDownPresenterImpl(mockAnalyticsManager, mockHumanlyReadableDurationsConverter, mockPreferencesPersistor, mockAudioInformationProvider)
    }

    @Test
    fun `When timer start the view should switch to countdown mode`() {
        // Given
        val spyCountDownPresenter = Mockito.spy(countDownPresenter)
        doNothing().`when`(spyCountDownPresenter).restartTimer()
        Mockito.`when`(mockPreferencesPersistor.getInitialDuration()).thenReturn(42)
        spyCountDownPresenter.screen = mockCountDownScreen
        spyCountDownPresenter.speakerCounter = 16

        // When
        spyCountDownPresenter.startTimer()

        // Then
        verify(mockCountDownScreen).setTimerVisibility(true)
        verify(mockCountDownScreen).setStopAvailability(true)
        verify(mockCountDownScreen).setTimerValue("42s")
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
    fun `When countdown is canceled the view should reinit to landpage mode`() {
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
    fun `When CountDownTimer does not exist then restartTimer should reinit a new one`() {
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
    fun `When a CountDownTimer is running restartTimer should cancel it and relaunch a new one`() {
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
    fun `Displayed speaker counter should be increased when increaseSpeakerCounter is called`() {
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
    fun `When loop mode is activated onCountDownOver should notify timer ends and restart a new timer`() {
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
    fun `When step by step mode is activated onCountDownOver should only trigger timer end`() {
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
    fun `When countdown is updated then it should be displayed on the view`() {
        // Given
        countDownPresenter.screen = mockCountDownScreen

        // When
        countDownPresenter.onValueUpdated(42)

        // Then
        verify(mockCountDownScreen).setTimerValue("42s")
        verifyNoMoreInteractions(mockCountDownScreen)
    }

    @Test
    fun `When there is a new speaker the speaker counter should be increased`() {
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
    fun `When action feedback is clicked then feedback view should be displayed`() {
        // Given
        countDownPresenter.screen = mockCountDownScreen

        // When
        countDownPresenter.onActionFeedbackClicked()

        // Then
        verify(mockCountDownScreen).sendFeedbackEmailAction()
        verify(mockAnalyticsManager).logSendFeedback()
    }

    @Test
    fun `When sound is muted then we should display a warning message`() {
        // Given
        countDownPresenter.screen = mockCountDownScreen
        Mockito.`when`(mockAudioInformationProvider.isSoundMuted()).thenReturn(true)

        // When
        countDownPresenter.onScreenFirstDisplay()

        // Then
        Mockito.verify(mockCountDownScreen).displaySoundMutedMessage()
    }

    @Test
    fun `When sound is not muted then we should not display a warning message`() {
        // Given
        countDownPresenter.screen = mockCountDownScreen
        Mockito.`when`(mockAudioInformationProvider.isSoundMuted()).thenReturn(false)

        // When
        countDownPresenter.onScreenFirstDisplay()

        // Then
        Mockito.verifyZeroInteractions(mockCountDownScreen)
    }


}
