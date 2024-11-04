package com.aowen.pokedex.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aowen.pokedex.R
import com.aowen.pokedex.data.PokemonCardRepository
import com.aowen.pokedex.data.PokemonCardUiData
import com.aowen.pokedex.datastore.UserPreferencesRepository
import com.aowen.pokedex.network.NetworkResult
import com.aowen.pokedex.ui.util.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeScreenUiState {
    data object Loading : HomeScreenUiState()
    data class Loaded(
        val allSets: List<String>,
        val selectedSets: List<String>,
        val selectedCard: PokemonCardUiData? = null,
        val errorMessage: UiString? = null
    ) : HomeScreenUiState()
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    tcgPokemonCardRepository: PokemonCardRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val userPreferencesFlow = userPreferencesRepository.userPreferences

    private val _uiState: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState.Loading)
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            val result = async { tcgPokemonCardRepository.getAllSetNames() }.await()
            userPreferencesFlow.collectLatest { userPreferencesData ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            HomeScreenUiState.Loaded(
                                allSets = result.body,
                                selectedSets = userPreferencesData.selectedSets
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            HomeScreenUiState.Loaded(
                                allSets = emptyList(),
                                selectedSets = userPreferencesData.selectedSets,
                                errorMessage = UiString.StringResource(R.string.error_network)
                            )
                        }
                    }

                    is NetworkResult.Exception -> {
                        _uiState.update {
                            HomeScreenUiState.Loaded(
                                allSets = emptyList(),
                                selectedSets = userPreferencesData.selectedSets,
                                errorMessage = UiString.StringResource(R.string.error_unknown)
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAddSelectedSet(set: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateSelectedSets(set)
        }
    }

    fun onRemoveSelectedSet(set: String) {
        viewModelScope.launch {
            userPreferencesRepository.removeSelectedSet(set)
        }
    }

    fun onSetSelectedCard(card: PokemonCardUiData) {
        _uiState.update { currentState ->
            if (currentState is HomeScreenUiState.Loaded) {
                currentState.copy(selectedCard = card)
            } else {
                currentState
            }
        }
    }

    fun onClearSelectedCard() {
        _uiState.update { currentState ->
            if (currentState is HomeScreenUiState.Loaded) {
                currentState.copy(selectedCard = null)
            } else {
                currentState
            }
        }
    }
}
