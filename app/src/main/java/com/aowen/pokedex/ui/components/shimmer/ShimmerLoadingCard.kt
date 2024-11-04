package com.aowen.pokedex.ui.components.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoadingCard() {
    Box(
        modifier = Modifier
            .heightIn(max = 250.dp)
            .aspectRatio(0.7f)
            .loadingShimmerAnimation()
    )
}