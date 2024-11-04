package com.aowen.pokedex.ui.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiString {

    data class DynamicString(
        val value: String?,
        @StringRes val fallbackResId: Int
    ) : UiString()

    data object Empty : UiString()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiString()

    fun asString(context: Context?): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value ?: context?.getString(fallbackResId).orEmpty()
            is StringResource -> context?.getString(resId, *args).orEmpty()
        }
    }
}