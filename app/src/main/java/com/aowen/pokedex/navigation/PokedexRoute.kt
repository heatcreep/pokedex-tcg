package com.aowen.pokedex.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class PokedexRoute {

    @Serializable
    data object HomeRoute : PokedexRoute()

    @Serializable
    data class PokemonCardDetailsRoute(val cardId: String) : PokedexRoute()

    @Serializable
    data class SetListRoute(val setName: String) : PokedexRoute()
}