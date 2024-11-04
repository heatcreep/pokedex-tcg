package com.aowen.pokedex.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.aowen.pokedex.R

@Composable
fun AsyncCardImage(
    cardName: String,
    imageUrl: String,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val model = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .build()
    AsyncImage(
        modifier = Modifier
            .heightIn(
                max = 400.dp
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable {
                        onClick()
                    }
                } else {
                    Modifier
                }
            ),
        fallback = painterResource(id = R.drawable.pokeball_icon),
        error = painterResource(id = R.drawable.pokeball_icon),
        model = model,
        contentDescription = stringResource(R.string.cd_card_image, cardName)
    )
}