package com.aowen.pokedex.fakes

import com.aowen.pokedex.fakes.data.fakePokemonCardNetworkErrorResponse
import com.aowen.pokedex.fakes.data.fakePokemonCardsNetworkErrorResponse
import com.aowen.pokedex.fakes.data.fakePokemonSetNetworkErrorResponse
import com.aowen.pokedex.fakes.data.fakePokemonSetNetworkInternalServerError
import com.aowen.pokedex.fakes.data.fakeSuccessfulPokemonCardDataDto
import com.aowen.pokedex.fakes.data.fakeSuccessfulPokemonSetDataDto
import com.aowen.pokedex.fakes.data.fakeSuccessfulPokemonCardsDataDto
import com.aowen.pokedex.network.PokemonTcgApiService
import com.aowen.pokedex.network.model.PokemonCardDataDto
import com.aowen.pokedex.network.model.PokemonCardsDataDto
import com.aowen.pokedex.network.model.PokemonSetDataDto
import retrofit2.Response

abstract class CardsByQueryUseCase {
    data object Success : CardsByQueryUseCase()
    data object Error : CardsByQueryUseCase()
    data object Exception : CardsByQueryUseCase()
}

abstract class CardByIdUseCase {
    data object Success : CardByIdUseCase()
    data object Error : CardByIdUseCase()
    data object Exception : CardByIdUseCase()
}

abstract class SetsUseCase {
    data object Success : SetsUseCase()
    data object Error : SetsUseCase()
    data object Exception : SetsUseCase()
}

class FakePokemonTcgApiService(
    private val cardsByQueryUseCase: CardsByQueryUseCase = CardsByQueryUseCase.Success,
    private val setsUseCase: SetsUseCase = SetsUseCase.Success
) : PokemonTcgApiService {
    override suspend fun getCardsByQuery(
        query: String,
        orderBy: String,
        pageSize: Int?
    ): Response<PokemonCardsDataDto> {
        return when (cardsByQueryUseCase) {
            is CardsByQueryUseCase.Success -> Response.success(
                fakeSuccessfulPokemonCardsDataDto
            )
            is CardsByQueryUseCase.Error -> fakePokemonCardsNetworkErrorResponse

            is CardsByQueryUseCase.Exception -> throw Exception("An exception occurred")
            else -> fakePokemonCardsNetworkErrorResponse
        }
    }

    override suspend fun getCardById(id: String): Response<PokemonCardDataDto> {
        return when (cardsByQueryUseCase) {
            is CardsByQueryUseCase.Success -> Response.success(
                fakeSuccessfulPokemonCardDataDto
            )
            is CardsByQueryUseCase.Error -> fakePokemonCardNetworkErrorResponse

            is CardsByQueryUseCase.Exception -> throw Exception("An exception occurred")
            else -> fakePokemonCardNetworkErrorResponse
        }
    }

    override suspend fun getAllSets(orderBy: String): Response<PokemonSetDataDto> {
        return when (setsUseCase) {
            is SetsUseCase.Success -> {
                return Response.success(
                    fakeSuccessfulPokemonSetDataDto
                )
            }

            is SetsUseCase.Error -> return fakePokemonSetNetworkErrorResponse
            is SetsUseCase.Exception -> throw Exception("An exception occurred")
            else -> fakePokemonSetNetworkInternalServerError
        }
    }
}