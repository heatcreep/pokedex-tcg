package com.aowen.pokedex.ui.components.cardrow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aowen.pokedex.R
import com.aowen.pokedex.data.PokemonCardRepository
import com.aowen.pokedex.data.PokemonCardUiData
import com.aowen.pokedex.network.NetworkResult
import com.aowen.pokedex.ui.util.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


abstract class CardRowUiData {

    data object Loading : CardRowUiData()
    data class Loaded(
        val title: String,
        val cards: List<PokemonCardUiData>
    ) : CardRowUiData()

    data class Error(val message: UiString) : CardRowUiData()
}

@HiltViewModel
class CardRowViewModel @Inject constructor(
    private val pokemonCardRepository: PokemonCardRepository
) : ViewModel() {

    private val _cardRowState: MutableStateFlow<CardRowUiData> =
        MutableStateFlow(CardRowUiData.Loading)
    val cardRowState: StateFlow<CardRowUiData> = _cardRowState

    fun initViewModel(set: String) {
        viewModelScope.launch {
            _cardRowState.update { CardRowUiData.Loading }
            when (
                val result = pokemonCardRepository.getCardsByQuery(
                    query = "!set.name:\"$set\"",
                    pageSize = 10
                )
            ) {
                is NetworkResult.Error -> {
                    _cardRowState.update {
                        CardRowUiData.Error(
                            message = UiString.StringResource(R.string.error_unknown)
                        )
                    }
                }

                is NetworkResult.Exception -> {
                    _cardRowState.update {
                        CardRowUiData.Error(
                            message = UiString.StringResource(R.string.error_unknown)
                        )
                    }
                }

                is NetworkResult.Success -> {
                    _cardRowState.update {
                        CardRowUiData.Loaded(
                            title = set,
                            cards = result.body
                        )
                    }
                }
            }
        }
    }
}