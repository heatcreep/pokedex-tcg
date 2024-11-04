@file:OptIn(ExperimentalCoroutinesApi::class)
package com.aowen.pokedex.ui.components

import com.aowen.pokedex.MainDispatcherRule
import com.aowen.pokedex.fakes.FakePokemonCardRepository
import com.aowen.pokedex.fakes.GetCardsUseCase
import com.aowen.pokedex.fakes.data.fakePokemonCardUiData
import com.aowen.pokedex.ui.components.cardrow.CardRowUiData
import com.aowen.pokedex.ui.components.cardrow.CardRowViewModel
import com.aowen.pokedex.ui.util.UiString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CardRowViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeSetName = "testSet"

    private lateinit var viewModel: CardRowViewModel

    @Before
    fun setup() {
        viewModel = CardRowViewModel(
            pokemonCardRepository = FakePokemonCardRepository()
        )
    }

    @Test
    fun `CardRowUiData should be Loaded when view model inits with a NetworkResultSuccess`() = runTest {
        // Given
        viewModel.initViewModel(fakeSetName)
        // When
        advanceUntilIdle()
        // Then
        assertEquals(
            CardRowUiData.Loaded(
                title = "testSet",
                cards = listOf(fakePokemonCardUiData)
            ),
            viewModel.cardRowState.value
        )
    }

    @Test
    fun `CardRowUiData should be Error when view model inits with a NetworkResultError`() = runTest {
        // Given
        viewModel = CardRowViewModel(
            pokemonCardRepository = FakePokemonCardRepository(
                useCase = GetCardsUseCase.Error
            )
        )
        // When
        viewModel.initViewModel(fakeSetName)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.cardRowState.value is CardRowUiData.Error)
        assertTrue((viewModel.cardRowState.value as CardRowUiData.Error).message is UiString.StringResource)
    }

    @Test
    fun `CardRowUiData should be Error when view model inits with a NetworkResultException`() = runTest {
        // Given
        viewModel = CardRowViewModel(
            pokemonCardRepository = FakePokemonCardRepository(
                useCase = GetCardsUseCase.Exception
            )
        )
        // When
        viewModel.initViewModel(fakeSetName)
        advanceUntilIdle()
        // Then
        assertTrue(viewModel.cardRowState.value is CardRowUiData.Error)
        assertTrue((viewModel.cardRowState.value as CardRowUiData.Error).message is UiString.StringResource)
    }

}