package com.aowen.pokedex.network

import com.aowen.pokedex.network.model.PokemonCardDataDto
import com.aowen.pokedex.network.model.PokemonCardsDataDto
import com.aowen.pokedex.network.model.PokemonSetDataDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonTcgApiService {

    /**
     * Gets a list of Pokemon cards by query
     * @param query The query to search for e.g. for set "set.id:base4"
     * @param pageSize The number of cards to return
     * @param orderBy The order to return the cards in
     * default is by number
     */
    @GET("cards")
    suspend fun getCardsByQuery(
        @Query("q") query: String = "set.id:base4",
        @Query("orderBy") orderBy: String = "number",
        @Query("pageSize") pageSize: Int?
    ): Response<PokemonCardsDataDto>

    /**
     * Gets a Pokemon card by id
     * @param id The id of the card to get
     */
    @GET("cards/{id}")
    suspend fun getCardById(
        @Path("id") id: String
    ): Response<PokemonCardDataDto>

    /**
     * Gets a list of all Pokemon sets
     * @param orderBy The order to return the sets in
     * default is by name
     */
    @GET("sets")
    suspend fun getAllSets(
        @Query("orderBy") orderBy: String = "name"
    ): Response<PokemonSetDataDto>
}