package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import com.octo.mob.octomeuh.countdown.screen.RepetitionModeSelectorScreen
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RepetitionModeSelectorPresenterImplTest {

    lateinit var mockPreferencesPersistor : PreferencesPersistor
    lateinit var mockScreen : RepetitionModeSelectorScreen
    lateinit var repetitionModeSelectorPresenter: RepetitionModeSelectorPresenterImpl

    @Before
    fun setUp() {
        mockPreferencesPersistor = Mockito.mock(PreferencesPersistor::class.java)
        mockScreen = Mockito.mock(RepetitionModeSelectorScreen::class.java)
        repetitionModeSelectorPresenter = RepetitionModeSelectorPresenterImpl(mockPreferencesPersistor)
    }

    @Test
    fun testAttach_WhenStoredModeIsLoop_ShouldPreselectLoopMode() {
        // Given
        Mockito.`when`(mockPreferencesPersistor.getRepetitionMode()).thenReturn(RepetitionMode.LOOP)

        // When
        repetitionModeSelectorPresenter.attach(mockScreen)

        // Then
        Mockito.verify(mockScreen).setLoopModeSelected(true)
        Mockito.verify(mockScreen).setStepByStepSelected(false)
        Mockito.verifyNoMoreInteractions(mockScreen)
    }

    @Test
    fun testAttach_WhenStoredModeIsStepByStep_ShouldPreselectStepByStepMode() {
        // Given
        Mockito.`when`(mockPreferencesPersistor.getRepetitionMode()).thenReturn(RepetitionMode.STEP_BY_STEP)

        // When
        repetitionModeSelectorPresenter.attach(mockScreen)

        // Then
        Mockito.verify(mockScreen).setLoopModeSelected(false)
        Mockito.verify(mockScreen).setStepByStepSelected(true)
        Mockito.verifyNoMoreInteractions(mockScreen)
    }

    @Test
    fun testOnLoopModeSelected_ShouldSelectLoopModePersistStateAndDismissScreen() {
        // Given
        repetitionModeSelectorPresenter.screen = mockScreen

        // When
        repetitionModeSelectorPresenter.onLoopModeSelected()

        // Then
        Mockito.verify(mockScreen).setLoopModeSelected(true)
        Mockito.verify(mockScreen).setStepByStepSelected(false)
        Mockito.verify(mockScreen).dismissScreen()
        Mockito.verifyNoMoreInteractions(mockScreen)
        Mockito.verify(mockPreferencesPersistor).saveRepetitionMode(RepetitionMode.LOOP)
        Mockito.verifyNoMoreInteractions(mockPreferencesPersistor)

    }

    @Test
    fun testOnStepByStepModeSelected_ShouldSelectStepByStepModePersistStateAndDismissScreen() {
        // Given
        repetitionModeSelectorPresenter.screen = mockScreen

        // When
        repetitionModeSelectorPresenter.onStepByStepSelected()

        // Then
        Mockito.verify(mockScreen).setLoopModeSelected(false)
        Mockito.verify(mockScreen).setStepByStepSelected(true)
        Mockito.verify(mockScreen).dismissScreen()
        Mockito.verifyNoMoreInteractions(mockScreen)
        Mockito.verify(mockPreferencesPersistor).saveRepetitionMode(RepetitionMode.STEP_BY_STEP)
        Mockito.verifyNoMoreInteractions(mockPreferencesPersistor)

    }
}