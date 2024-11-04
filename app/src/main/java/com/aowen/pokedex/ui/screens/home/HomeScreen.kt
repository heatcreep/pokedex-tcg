package com.aowen.pokedex.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aowen.pokedex.R
import com.aowen.pokedex.data.PokemonCardUiData
import com.aowen.pokedex.ui.components.FullScreenCardImageDialog
import com.aowen.pokedex.ui.components.FullScreenLoadingWithMessage
import com.aowen.pokedex.ui.components.PokedexTopAppBar
import com.aowen.pokedex.ui.components.cardrow.CardRow
import com.aowen.pokedex.ui.screens.pokemondetails.navigation.navigateToPokemonCardDetails
import com.aowen.pokedex.ui.screens.setlist.navigation.navigateToSetList

@Composable
fun HomeScreenRoute(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    var isSetListSelectionOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            PokedexTopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = {
                            isSetListSelectionOpen = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.cd_edit_tracked_sets)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            HomeScreen(
                uiState = uiState,
                isSetListSelectionOpen = isSetListSelectionOpen,
                addSelectedSet = viewModel::onAddSelectedSet,
                removeSelectedSet = viewModel::onRemoveSelectedSet,
                addSelectedCard = viewModel::onSetSelectedCard,
                removeSelectedCard = viewModel::onClearSelectedCard,
                handleCloseSetList = {
                    isSetListSelectionOpen = false
                },
                navigateToSetList = navController::navigateToSetList,
                navigateToCardDetails = navController::navigateToPokemonCardDetails
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    isSetListSelectionOpen: Boolean,
    addSelectedSet: (String) -> Unit,
    removeSelectedSet: (String) -> Unit,
    addSelectedCard: (PokemonCardUiData) -> Unit,
    removeSelectedCard: () -> Unit,
    handleCloseSetList: () -> Unit,
    navigateToSetList: (String) -> Unit,
    navigateToCardDetails: (String) -> Unit
) {

    val context = LocalContext.current

    when (uiState) {
        is HomeScreenUiState.Loading -> {
            FullScreenLoadingWithMessage(
                messageText = stringResource(R.string.loading_tracked_sets),
            )
        }

        is HomeScreenUiState.Loaded -> {
            if (isSetListSelectionOpen) {
                ModalBottomSheet(
                    sheetState = rememberModalBottomSheetState(
                        skipPartiallyExpanded = true
                    ),
                    onDismissRequest = handleCloseSetList
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(R.string.mbs_title_select_stats),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(uiState.allSets) { set ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = uiState.selectedSets.contains(set),
                                        onCheckedChange = {
                                            if (uiState.selectedSets.contains(set)) {
                                                removeSelectedSet(set)
                                            } else {
                                                addSelectedSet(set)
                                            }
                                        }
                                    )
                                    Text(
                                        text = set,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = handleCloseSetList
                            ) {
                                Text(stringResource(R.string.btn_close))
                            }
                        }
                    }
                }
            }
            if (uiState.selectedCard != null) {
                val selectedCard = uiState.selectedCard
                FullScreenCardImageDialog(
                    imageUrl = selectedCard.imageUrl,
                    imageContentDescription = stringResource(
                        R.string.cd_card_image,
                        selectedCard.name
                    ),
                    onNavigation = {
                        navigateToCardDetails(selectedCard.id)
                        removeSelectedCard()
                    },
                    handleOnDismiss = removeSelectedCard
                )
            }
            Column(
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                uiState.errorMessage?.let { errorMessage ->
                    Text(
                        text = errorMessage.asString(context),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(uiState.selectedSets) { set ->
                        CardRow(
                            set = set,
                            onClick = { card ->
                                addSelectedCard(card)
                            },
                            onViewAll = {
                                navigateToSetList(set)
                            }
                        )
                    }
                }
            }
        }
    }
}