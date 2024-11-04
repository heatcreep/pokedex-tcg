package com.aowen.pokedex.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.aowen.pokedex.R

@Composable
fun FullScreenCardImageDialog(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageContentDescription: String,
    onNavigation: () -> Unit,
    handleOnDismiss: () -> Unit,
) {

    val context = LocalContext.current

    Dialog(
        onDismissRequest = {
            handleOnDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f))
                .padding(16.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = {
                    handleOnDismiss()
                },
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.btn_close)
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val model = ImageRequest.Builder(context)
                        .data(imageUrl)
                        .crossfade(true)
                        .build()
                    // Display the card
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onError = { error ->
                            Log.d("HomeScreen", "Error loading image: $error")
                        },
                        model = model,
                        contentDescription = imageContentDescription
                    )
                    TextButton(
                        onClick = onNavigation
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Go to card details",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                contentDescription = stringResource(id = R.string.cd_go_to_card_details)
                            )
                        }
                    }
                }
            }
        }
    }
}