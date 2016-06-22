package com.octo.mob.octomeuh.transversal

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class ExtensionsKtTest {

    @Test
    fun testWithNullable_WhenObjectIsNull_ShouldNotCrash() {
        // Given
        val nullFoo : FooInterface?  = null

        // When
        withNullable(nullFoo) {
            foo()
        }

        // Then no error occurred
    }

    @Test
    fun testWithNullable_WhenIsNotNull_ShouldCallSubmethods() {
        // Given
        val fooObject : FooInterface  = mock(FooInterface::class.java)

        // When
        withNullable(fooObject) {
            foo()
        }

        // Then no error occurred
        Mockito.verify(fooObject).foo()
    }

    private interface FooInterface {
        fun foo()
    }
}