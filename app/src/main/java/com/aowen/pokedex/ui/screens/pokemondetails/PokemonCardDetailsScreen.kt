package com.aowen.pokedex.ui.screens.pokemondetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aowen.pokedex.R
import com.aowen.pokedex.ui.components.AsyncCardImage
import com.aowen.pokedex.ui.components.CardDetailStatRow
import com.aowen.pokedex.ui.components.FullScreenErrorWithRetry
import com.aowen.pokedex.ui.components.FullScreenLoadingWithMessage
import com.aowen.pokedex.ui.components.PokedexTopAppBar

@Composable
fun PokemonCardDetailsRoute(
    navController: NavController,
    viewModel: PokemonCardDetailsViewModel
) {

    val uiState by viewModel.pokemonCard.collectAsState()

    Scaffold(
        topBar = {
            PokedexTopAppBar(
                title = {
                    Text(text = stringResource(R.string.page_title_card_details))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PokemonCardDetailsScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            uiState = uiState,
            onRetry = viewModel::initViewModel
        )
    }
}

@Composable
fun PokemonCardDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: PokemonCardDetailsUiState,
    onRetry: () -> Unit,
) {

    val context = LocalContext.current


    when (uiState) {
        is PokemonCardDetailsUiState.Loading -> {
            FullScreenLoadingWithMessage(
                messageText = stringResource(R.string.loading_card_details)
            )
        }

        is PokemonCardDetailsUiState.Error -> {
            FullScreenErrorWithRetry(
                message = uiState.message.asString(context),
                onRetry = onRetry
            )
        }

        is PokemonCardDetailsUiState.Success -> {
            val pokemonCard = uiState.pokemonCard
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncCardImage(
                    cardName = pokemonCard.name,
                    imageUrl = pokemonCard.imageUrl,
                )
                // Name
                Text(
                    text = pokemonCard.name,
                    style = MaterialTheme.typography.titleLarge
                )
                pokemonCard.flavorText?.let { text ->
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                CardDetailStatRow(
                    label = stringResource(R.string.card_detail_label_cm_mid_price),
                    value = pokemonCard.cardMarketPrice
                )
                CardDetailStatRow(
                    label = stringResource(R.string.card_detail_label_rarity),
                    value = pokemonCard.rarity
                )
                CardDetailStatRow(
                    label = stringResource(R.string.card_detail_label_artist),
                    value = pokemonCard.artist
                )

            }

        }
    }

}