package com.aowen.pokedex.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSetDto(
    val name: String,
)

@Serializable
data class PokemonSetDataDto(
    val data: List<PokemonSetDto>
)

fun PokemonSetDataDto.toListOfSetNames() = data.map { it.name }
