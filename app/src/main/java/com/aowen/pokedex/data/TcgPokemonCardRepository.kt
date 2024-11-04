package com.aowen.pokedex.data

import com.aowen.pokedex.network.NetworkResult
import com.aowen.pokedex.network.PokemonTcgApiService
import com.aowen.pokedex.network.model.toListOfSetNames
import com.aowen.pokedex.network.model.toPokemonCardUiData
import com.aowen.pokedex.network.safeApiCall
import javax.inject.Inject

class TcgPokemonCardRepository @Inject constructor(
    private val pokemonTcgApiService: PokemonTcgApiService
) : PokemonCardRepository {

    override suspend fun getCardsByQuery(
        query: String,
        orderBy: String,
        pageSize: Int?
    ): NetworkResult<List<PokemonCardUiData>> {
        val result = safeApiCall {
            pokemonTcgApiService.getCardsByQuery(query, orderBy, pageSize)
        }
        return when (result) {
            is NetworkResult.Success -> {
                val cards = result.body.data.map { it.toPokemonCardUiData() }
                NetworkResult.Success(cards)
            }

            is NetworkResult.Error -> NetworkResult.Error(
                code = result.code,
                message = result.message
            )

            is NetworkResult.Exception -> NetworkResult.Exception(
                exception = result.exception
            )
        }
    }

    override suspend fun getCardById(id: String): NetworkResult<PokemonCardUiData> {
        val result = safeApiCall { pokemonTcgApiService.getCardById(id) }
        return when (result) {
            is NetworkResult.Success -> {
                val card = result.body.data.toPokemonCardUiData()
                NetworkResult.Success(card)
            }

            is NetworkResult.Error -> NetworkResult.Error(
                code = result.code,
                message = result.message
            )

            is NetworkResult.Exception -> NetworkResult.Exception(
                exception = result.exception
            )
        }
    }

    override suspend fun getAllSetNames(): NetworkResult<List<String>> {
        val result = safeApiCall {
            pokemonTcgApiService.getAllSets()
        }
        return when (result) {
            is NetworkResult.Success -> {
                val sets = result.body.toListOfSetNames()
                NetworkResult.Success(sets)
            }

            is NetworkResult.Error -> NetworkResult.Error(
                code = result.code,
                message = result.message
            )

            is NetworkResult.Exception -> NetworkResult.Exception(
                exception = result.exception
            )
        }
    }
}

