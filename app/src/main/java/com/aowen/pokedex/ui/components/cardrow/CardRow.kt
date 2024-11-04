package com.aowen.pokedex.ui.components.cardrow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aowen.pokedex.R
import com.aowen.pokedex.data.PokemonCardUiData
import com.aowen.pokedex.ui.components.AsyncCardImage
import com.aowen.pokedex.ui.components.FullScreenErrorWithRetry
import com.aowen.pokedex.ui.components.shimmer.ShimmerLoadingCard

@Composable
fun CardRow(
    set: String,
    onClick: (PokemonCardUiData) -> Unit,
    onViewAll: () -> Unit,
    viewModel: CardRowViewModel = hiltViewModel(key = set)
) {
    val cardRowState by viewModel.cardRowState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initViewModel(set)
    }

    CardContent(
        set = set,
        cardRowState = cardRowState,
        onViewAll = onViewAll,
        onClick = onClick,
        onRefresh = viewModel::initViewModel
    )
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    set: String,
    cardRowState: CardRowUiData,
    onViewAll: () -> Unit,
    onClick: (PokemonCardUiData) -> Unit,
    onRefresh: (String) -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        when (cardRowState) {
            is CardRowUiData.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = set,
                        style = MaterialTheme.typography.titleLarge
                            .copy(fontWeight = FontWeight.ExtraBold),
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(4) {
                            ShimmerLoadingCard()
                        }
                    }
                }
            }

            is CardRowUiData.Error -> {
                FullScreenErrorWithRetry(
                    message = cardRowState.message.asString(context),
                    onRetry = { onRefresh(set) }
                )
            }

            is CardRowUiData.Loaded -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    ) {
                        Text(
                            text = cardRowState.title,
                            style = MaterialTheme.typography.titleLarge
                                .copy(fontWeight = FontWeight.ExtraBold),
                        )
                        TextButton(
                            onClick = onViewAll
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.btn_label_view_all),
                            )
                            Text(
                                text = stringResource(R.string.btn_label_view_all),
                            )
                        }
                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(cardRowState.cards) { card ->
                            AsyncCardImage(
                                cardName = card.name,
                                imageUrl = card.imageUrl,
                                onClick = { onClick(card) }
                            )
                        }
                    }
                }
            }
        }
    }
}