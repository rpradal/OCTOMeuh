package com.octo.mob.octomeuh.transversal

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

@Suppress("IllegalIdentifier")
class ExtensionsKtTest {

    @Test
    fun `When object is null inner block is not called and no crash occurs`() {
        // Given
        val nullFoo : FooInterface?  = null

        // When
        withNullable(nullFoo) {
            foo()
        }

        // Then no error occurred
    }

    @Test
    fun `When object is not null method in block should be called`() {
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