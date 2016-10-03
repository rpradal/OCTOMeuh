package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.screen.RotatableScreen
import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(DataProviderRunner::class)
class UpsideDownPresenterImplTest {
    companion object {

        @DataProvider
        @JvmStatic
        fun positions(): Array<Any> = arrayOf(
                Pair(intArrayOf(0, 180, 0),                     1),
                Pair(intArrayOf(0, 10, 0),                      0),
                Pair(intArrayOf(0, 45, 0),                      0),
                Pair(intArrayOf(0, 10, 70, 20, 0),              0),
                Pair(intArrayOf(0, 10, 70, 90, 185, 5),         1),
                Pair(intArrayOf(0, 180, 355),                   1),
                Pair(intArrayOf(0, 180, 355, 0, 70, 177),       1),
                Pair(intArrayOf(0, 180, 355, 0, 70, 177, 4),    2),
                Pair(intArrayOf(177, 3, 185),                   1)
        )

    }

    @Test
    @UseDataProvider("positions")
    fun `Algorithm should comply with the different rotation scenarii`(testCaseScenario: Pair<IntArray, Int>) {
        // Given
        val positionArray = testCaseScenario.first
        val mockedRotatableScreen = mock(RotatableScreen::class.java)
        val presenter = UpsideDownPresenterImpl()
        presenter.attach(mockedRotatableScreen)

        // When
        positionArray.forEach { presenter.onAngleChanged(it) }

        // Then
        val upsideDownNumber = testCaseScenario.second
        verify(mockedRotatableScreen, times(upsideDownNumber)).onUpsideDown()
    }


}