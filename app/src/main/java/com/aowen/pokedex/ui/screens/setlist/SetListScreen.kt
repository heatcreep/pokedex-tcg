package com.aowen.pokedex.ui.screens.setlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aowen.pokedex.R
import com.aowen.pokedex.data.PokemonCardUiData
import com.aowen.pokedex.ui.components.AsyncCardImage
import com.aowen.pokedex.ui.components.FullScreenCardImageDialog
import com.aowen.pokedex.ui.components.FullScreenErrorWithRetry
import com.aowen.pokedex.ui.components.FullScreenLoadingWithMessage
import com.aowen.pokedex.ui.components.PokedexTopAppBar
import com.aowen.pokedex.ui.screens.pokemondetails.navigation.navigateToPokemonCardDetails

@Composable
fun SetListScreenRoute(
    navController: NavController,
    viewModel: SetListScreenViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val setName = viewModel.route.setName


    Scaffold(
        topBar = {
            PokedexTopAppBar(
                title = {
                    Text(text = setName)
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
        SetListScreen(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            setName = setName,
            handleCardDialogRequest = viewModel::setSelectedCard,
            handleCardDialogProcessed = viewModel::clearSelectedCard,
            navigateToPokemonDetails = navController::navigateToPokemonCardDetails,
            onRetry = viewModel::initViewModel
        )
    }
}

@Composable
fun SetListScreen(
    modifier: Modifier = Modifier,
    uiState: SetListUiState,
    handleCardDialogRequest: (PokemonCardUiData) -> Unit,
    handleCardDialogProcessed: () -> Unit,
    setName: String,
    navigateToPokemonDetails: (String) -> Unit,
    onRetry: () -> Unit
) {

    val context = LocalContext.current
    when (uiState) {
        is SetListUiState.Loading -> {
            // Loading state
            FullScreenLoadingWithMessage(
                messageText = stringResource(R.string.loading_card_set, setName)
            )
        }

        is SetListUiState.Error -> {
            // Error state
            FullScreenErrorWithRetry(
                message = uiState.message.asString(context),
                onRetry = onRetry
            )
        }

        is SetListUiState.Loaded -> {
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                uiState.selectedCard?.let { card ->
                    FullScreenCardImageDialog(
                        imageUrl = card.imageUrl,
                        imageContentDescription = stringResource(R.string.cd_card_image, card.name),
                        onNavigation = {
                            navigateToPokemonDetails(card.id)
                            handleCardDialogProcessed()
                        },
                        handleOnDismiss = {
                            handleCardDialogProcessed()
                        }
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(uiState.data) { pokemonCard ->
                        // CardRow
                        AsyncCardImage(
                            cardName = pokemonCard.name,
                            imageUrl = pokemonCard.imageUrl,
                            onClick = { handleCardDialogRequest(pokemonCard) }
                        )
                    }
                }
            }
        }
    }
}