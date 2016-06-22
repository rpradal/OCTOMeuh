package com.octo.mob.octomeuh.transversal

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class BasePresenterImplTest {

    lateinit var presenter : BasePresenterImpl<Screen>

    @Before
    fun setUp() {
        presenter = BasePresenterImpl()
    }

    @Test
    fun testAttach() {
        // Given
        val screen = Mockito.mock(Screen::class.java)

        // When
        presenter.attach(screen)

        // Then
        Assert.assertEquals(screen, presenter.screen)
    }

    @Test
    fun testDetach() {
        // Given
        val screen = Mockito.mock(Screen::class.java)
        presenter.attach(screen)

        // When
        presenter.detach()

        // Then
        Assert.assertEquals(null, presenter.screen)
    }

    interface Screen
}