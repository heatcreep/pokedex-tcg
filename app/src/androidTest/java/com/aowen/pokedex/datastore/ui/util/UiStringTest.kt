package com.aowen.pokedex.datastore.ui.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aowen.pokedex.R
import com.aowen.pokedex.ui.util.UiString
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiStringTest {

    private val fakeContext: Context = ApplicationProvider.getApplicationContext()

    private val testDynamicString = "testString"
    private val testFallbackString = "Pokedex TCG"

    @Test
    fun asString_returnsEmptyString_whenUiStringIsEmpty() {
        // Given
        val uiString = UiString.Empty
        // When
        val result = uiString.asString(fakeContext)
        // Then
        assert(result.isEmpty())
    }

    @Test
    fun asString_returnsDynamicString_whenUiStringIsDynamicString() {
        // Given
        val uiString = UiString.DynamicString(value = testDynamicString, fallbackResId = R.string.app_name)
        // When
        val result = uiString.asString(fakeContext)
        // Then
        assertEquals(testDynamicString, result)
    }

    @Test
    fun asString_returnsFallback_whenValueIsNull() {
        // Given
        val uiString = UiString.DynamicString(
            value = null,
            fallbackResId = R.string.app_name
        )
        // When
        val result = uiString.asString(fakeContext)
        // Then
        assertEquals(testFallbackString, result)
    }

    @Test
    fun asString_returnsStringResource_whenUiStringIsStringResource() {
        // Given
        val uiString = UiString.StringResource(resId = R.string.app_name)
        // When
        val result = uiString.asString(fakeContext)
        // Then
        assertEquals(testFallbackString, result)
    }
}