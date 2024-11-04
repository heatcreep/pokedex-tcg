package com.aowen.pokedex.ui.screens.pokemondetails

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
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class PokemonCardDetailsUiState {
    object Loading : PokemonCardDetailsUiState()
    data class Success(val pokemonCard: PokemonCardUiData) : PokemonCardDetailsUiState()
    data class Error(val message: UiString) : PokemonCardDetailsUiState()
}

@HiltViewModel
class PokemonCardDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonCardRepository: PokemonCardRepository
) : ViewModel() {

    private val route = savedStateHandle.toRoute<PokedexRoute.PokemonCardDetailsRoute>()

    private val _uiState: MutableStateFlow<PokemonCardDetailsUiState> =
        MutableStateFlow(PokemonCardDetailsUiState.Loading)
    val pokemonCard: StateFlow<PokemonCardDetailsUiState> = _uiState

    init {
        initViewModel()
    }

    fun initViewModel() {
        viewModelScope.launch {
            when (val result = pokemonCardRepository.getCardById(route.cardId)) {
                is NetworkResult.Success -> {
                    _uiState.value = PokemonCardDetailsUiState.Success(result.body)
                }

                is NetworkResult.Error -> {
                    _uiState.value = PokemonCardDetailsUiState.Error(
                        UiString.DynamicString(
                            result.message,
                            R.string.error_network
                        )
                    )
                }

                is NetworkResult.Exception -> {
                    _uiState.value = PokemonCardDetailsUiState.Error(
                        UiString.StringResource(
                            R.string.error_unknown
                        )
                    )
                }
            }
        }
    }

}
