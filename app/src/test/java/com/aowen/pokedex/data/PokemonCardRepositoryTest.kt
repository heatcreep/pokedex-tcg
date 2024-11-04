package com.aowen.pokedex.data

import com.aowen.pokedex.fakes.FakePokemonTcgApiService
import com.aowen.pokedex.fakes.data.fakeSuccessfulPokemonCardsDataDto
import com.aowen.pokedex.fakes.data.fakeSuccessfulPokemonSetDataDto
import com.aowen.pokedex.network.NetworkResult
import com.aowen.pokedex.network.model.toListOfSetNames
import com.aowen.pokedex.network.model.toPokemonCardUiData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonCardRepositoryTest {

    @Test
    fun `getCardsByQuery should return a list of PokemonCardUiData`() = runTest {

        // Given
        val repository = TcgPokemonCardRepository(
            pokemonTcgApiService = FakePokemonTcgApiService()
        )

        // When
        val result = repository.getCardsByQuery()

        // Then
        val expected = fakeSuccessfulPokemonCardsDataDto.data.map {
            it.toPokemonCardUiData()
        }

        assertEquals(expected, (result as NetworkResult.Success).body)
    }

    @Test
    fun `getAllSetNames should return a list of set names`() = runTest {

        // Given
        val repository = TcgPokemonCardRepository(
            pokemonTcgApiService = FakePokemonTcgApiService()
        )

        // When
        val result = repository.getAllSetNames()

        // Then
        val expected = fakeSuccessfulPokemonSetDataDto.toListOfSetNames()

        assertEquals(expected, (result as NetworkResult.Success).body)
    }
}