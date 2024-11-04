package com.aowen.pokedex.fakes

import com.aowen.pokedex.data.PokemonCardRepository
import com.aowen.pokedex.data.PokemonCardUiData
import com.aowen.pokedex.fakes.data.fakePokemonCardUiData
import com.aowen.pokedex.network.NetworkResult

abstract class GetCardsUseCase {
    data object Success : GetCardsUseCase()
    data object Error : GetCardsUseCase()
    data object Exception : GetCardsUseCase()
}

class FakePokemonCardRepository(
    private val useCase: GetCardsUseCase = GetCardsUseCase.Success
) : PokemonCardRepository {

    override suspend fun getCardsByQuery(
        query: String,
        orderBy: String,
        pageSize: Int?
    ): NetworkResult<List<PokemonCardUiData>> {
        return when (useCase) {
            is GetCardsUseCase.Success -> {
                NetworkResult.Success(
                    listOf(
                        fakePokemonCardUiData
                    )
                )
            }

            is GetCardsUseCase.Error -> {
                NetworkResult.Error(
                    code = 404,
                    message = "Not found"
                )
            }

            else -> {
                NetworkResult.Exception(
                    exception = Exception("An exception occurred")
                )
            }
        }
    }

    override suspend fun getCardById(id: String): NetworkResult<PokemonCardUiData> {
        return when (useCase) {
            is GetCardsUseCase.Success -> {
                NetworkResult.Success(
                    fakePokemonCardUiData
                )
            }

            is GetCardsUseCase.Error -> {
                NetworkResult.Error(
                    code = 404,
                    message = "Not found"
                )
            }

            else -> {
                NetworkResult.Exception(
                    exception = Exception("An exception occurred")
                )
            }
        }
    }

    override suspend fun getAllSetNames(): NetworkResult<List<String>> {
        return when (useCase) {
            is GetCardsUseCase.Success -> {
                NetworkResult.Success(
                    listOf("Base", "Jungle", "Fossil")
                )
            }

            is GetCardsUseCase.Error -> {
                NetworkResult.Error(
                    code = 404,
                    message = "Not found"
                )
            }

            else -> {
                NetworkResult.Exception(
                    exception = Exception("An exception occurred")
                )
            }
        }
    }
}