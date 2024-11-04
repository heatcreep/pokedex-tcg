package com.aowen.pokedex.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopAppBar(
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
) {
    TopAppBar(
        title = {
            title()
        },
        navigationIcon = {
            navigationIcon()
        },
        actions = {
            actions()
        },
        windowInsets = WindowInsets(0, 0, 0, 0)
    )
}