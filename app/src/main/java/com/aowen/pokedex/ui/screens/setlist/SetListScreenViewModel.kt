package com.aowen.pokedex.ui.screens.setlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.aowen.pokedex.R
import com.aowen.pokedex.data.PokemonCardRepository
import com.aowen.pokedex.data.PokemonCardUiData
import com.aowen.pokedex.navigation.PokedexRoute
import com.aowen.pokedex.network.NetworkResult
import com.aowen.pokedex.ui.util.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SetListUiState {
    data object Loading : SetListUiState()
    data class Error(val message: UiString) : SetListUiState()
    data class Loaded(
        val selectedCard: PokemonCardUiData? = null,
        val data: List<PokemonCardUiData>
    ) : SetListUiState()
}

@HiltViewModel
class SetListScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonCardRepository: PokemonCardRepository
) : ViewModel() {

    val route = savedStateHandle.toRoute<PokedexRoute.SetListRoute>()

    private val _uiState = MutableStateFlow<SetListUiState>(SetListUiState.Loading)
    val uiState: StateFlow<SetListUiState> = _uiState

    init {
        initViewModel()
    }

    fun initViewModel() {
        viewModelScope.launch {
            when (
                val result = pokemonCardRepository.getCardsByQuery(
                    query = "set.name:${route.setName}"
                )
            ) {
                is NetworkResult.Error -> {
                    _uiState.update {
                        SetListUiState.Error(
                            UiString.DynamicString(
                                result.message,
                                R.string.error_network
                            )
                        )
                    }
                }

                is NetworkResult.Exception -> {
                    _uiState.update {
                        SetListUiState.Error(
                            UiString.StringResource(
                                R.string.error_unknown
                            )
                        )
                    }
                }

                is NetworkResult.Success -> {
                    _uiState.update {
                        SetListUiState.Loaded(
                            data = result.body
                        )
                    }
                }
            }
        }
    }

    fun setSelectedCard(card: PokemonCardUiData) {
        _uiState.update {
            SetListUiState.Loaded(
                selectedCard = card,
                data = (it as SetListUiState.Loaded).data
            )
        }
    }

    fun clearSelectedCard() {
        _uiState.update {
            SetListUiState.Loaded(
                selectedCard = null,
                data = (it as SetListUiState.Loaded).data
            )
        }
    }
}