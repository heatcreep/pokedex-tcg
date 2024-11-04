package com.aowen.pokedex.fakes.data


import com.aowen.pokedex.network.model.PokemonCardDataDto
import com.aowen.pokedex.network.model.PokemonCardsDataDto
import com.aowen.pokedex.network.model.PokemonSetDataDto
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

val fakePokemonSetNetworkErrorResponse: Response<PokemonSetDataDto> = Response.error(
    404,
    "Not found".toResponseBody(null)
)

val fakePokemonCardNetworkErrorResponse: Response<PokemonCardDataDto> = Response.error(
    404,
    "Not found".toResponseBody(null)
)

val fakePokemonCardsNetworkErrorResponse: Response<PokemonCardsDataDto> = Response.error(
    404,
    "Not found".toResponseBody(null)
)

val fakePokemonSetNetworkInternalServerError: Response<PokemonSetDataDto> = Response.error(500, "Internal server error".toResponseBody(null))

val fakePokemonCardNetworkInternalServerError: Response<PokemonCardDataDto> = Response.error(500, "Internal server error".toResponseBody(null))