package com.aowen.pokedex.data

import kotlinx.serialization.Serializable

@Serializable
data class PokemonCardUiData(
    val id: String,
    val name: String,
    val supertype: String,
    val imageUrl: String,
    val cardMarketPrice: String,
    val artist: String,
    val flavorText: String?,
    val rarity: String
)
