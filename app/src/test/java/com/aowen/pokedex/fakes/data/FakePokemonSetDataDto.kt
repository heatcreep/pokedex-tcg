package com.aowen.pokedex.fakes.data

import com.aowen.pokedex.network.model.PokemonSetDataDto
import com.aowen.pokedex.network.model.PokemonSetDto

val fakeSuccessfulPokemonSetDataDto = PokemonSetDataDto(
    data = listOf(
        PokemonSetDto(
            name = "Base",
        )
    )
)