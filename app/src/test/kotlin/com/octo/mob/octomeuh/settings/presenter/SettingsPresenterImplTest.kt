package com.octo.mob.octomeuh.settings.presenter

import com.octo.mob.octomeuh.countdown.manager.PreferencesPersistor
import com.octo.mob.octomeuh.countdown.model.RepetitionMode
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverter
import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.screen.SettingsScreen
import com.octo.mob.octomeuh.settings.utils.AppInformation
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SettingsPresenterImplTest {

    @Mock
    lateinit var preferencePersistor: PreferencesPersistor

    @Mock
    lateinit var settingsScreen: SettingsScreen

    @Mock
    lateinit var appInformation: AppInformation

    @Mock
    lateinit var humanlyReadablenformation: HumanlyReadableDurationsConverter

    lateinit var settingsPresenter: SettingsPresenterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        settingsPresenter = SettingsPresenterImpl(preferencePersistor, humanlyReadablenformation, appInformation)
    }

    @Test
    fun attach_ShouldInitDefaultRepetitionModeValue() {
        // Given
        Mockito.`when`(preferencePersistor.getRepetitionMode()).thenReturn(RepetitionMode.STEP_BY_STEP)

        // When
        settingsPresenter.attach(settingsScreen)

        // Then
        Mockito.verify(settingsScreen).showCurrentRepetitionMode(RepetitionModeDescription.STEP_BY_STEP)
    }

    @Test
    fun onRepetitionModeChangeRequest_WhenCurrentDurationIsStepByStep_ShouldShowDurationSelectionWithStepByStepSelected() {
        // Given
        settingsPresenter.attach(settingsScreen)
        Mockito.`when`(preferencePersistor.getRepetitionMode()).thenReturn(RepetitionMode.STEP_BY_STEP)

        // When
        settingsPresenter.onRepetitionModeChangeRequest()

        // Then
        Mockito.verify(settingsScreen).showRepetitionModeSelection(RepetitionModeDescription.values(), 1)
    }

    @Test
    fun onRepetitionModeChangeRequest_WhenCurrentDurationIsLoop_ShouldShowDurationSelectionWithLoopSelected() {
        // Given
        settingsPresenter.attach(settingsScreen)
        Mockito.`when`(preferencePersistor.getRepetitionMode()).thenReturn(RepetitionMode.LOOP)

        // When
        settingsPresenter.onRepetitionModeChangeRequest()

        // Then
        Mockito.verify(settingsScreen).showRepetitionModeSelection(RepetitionModeDescription.values(), 0)
    }

    @Test
    fun onRepetitionModeChangeRequest() {
        // Given
        settingsPresenter.attach(settingsScreen)

        // When
        settingsPresenter.onDurationChangeRequest()

        // Then
        Mockito.verify(settingsScreen).showDurationSelection()
    }

    @Test
    fun onRepetitionModeSelected_ShouldSaveNewModeAndUpdateValueDisplayed() {
        // Given
        settingsPresenter.attach(settingsScreen)

        // When
        settingsPresenter.onRepetitionModeSelected(RepetitionModeDescription.STEP_BY_STEP)

        // Then
        Mockito.verify(settingsScreen).showCurrentRepetitionMode(RepetitionModeDescription.STEP_BY_STEP)
        Mockito.verify(preferencePersistor).saveRepetitionMode(RepetitionMode.STEP_BY_STEP)

    }

}