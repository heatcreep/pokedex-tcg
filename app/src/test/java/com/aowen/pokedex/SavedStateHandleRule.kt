package com.aowen.pokedex

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.internalToRoute
import com.aowen.pokedex.navigation.PokedexRoute
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Reusable JUnit4 TestRule to mock SavedStateHandle to return a route.
 * This is a workaround until Google fixes tests that depend SavedStateHandle.toRoute()
 * to be instrumented tests
 */
class SavedStateHandleRule(
    private val route: PokedexRoute,
) : TestWatcher() {
    val savedStateHandleMock: SavedStateHandle = mockk()
    override fun starting(description: Description?) {
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandleMock.internalToRoute<Any>(any(), any()) } returns route
        super.starting(description)
    }

    override fun finished(description: Description?) {
        unmockkStatic("androidx.navigation.SavedStateHandleKt")
        super.finished(description)
    }
}