package com.aowen.pokedex.data

import com.aowen.pokedex.network.NetworkResult

interface PokemonCardRepository {

    /**
     * Get a list of Pokemon cards by a query.
     * @param query The query to search for cards
     * @param orderBy The order to sort the cards by
     * this defaults to sorting by card number
     * @param pageSize The number of cards to return
     */
    suspend fun getCardsByQuery(
        query: String = "set.id:base4",
        orderBy: String = "number",
        pageSize: Int? = null
    ): NetworkResult<List<PokemonCardUiData>>

    /**
     * Get a Pokemon card by its id
     * @param id The id of the card to get
     */
    suspend fun getCardById(id: String): NetworkResult<PokemonCardUiData>

    /**
     * Get a list of all set names
     */
    suspend fun getAllSetNames(): NetworkResult<List<String>>
}