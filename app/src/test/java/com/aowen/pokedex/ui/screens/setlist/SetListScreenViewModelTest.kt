@file:OptIn(ExperimentalCoroutinesApi::class)

package com.aowen.pokedex.ui.screens.setlist

import com.aowen.pokedex.MainDispatcherRule
import com.aowen.pokedex.SavedStateHandleRule
import com.aowen.pokedex.fakes.FakePokemonCardRepository
import com.aowen.pokedex.fakes.GetCardsUseCase
import com.aowen.pokedex.fakes.data.fakePokemonCardUiData
import com.aowen.pokedex.navigation.PokedexRoute
import com.aowen.pokedex.ui.util.UiString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SetListScreenViewModelTest {

    private val route = PokedexRoute.SetListRoute(setName = "Base")

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val savedStateHandleRule = SavedStateHandleRule(route)

    private lateinit var viewModel: SetListScreenViewModel


    @Before
    fun setup() {
        viewModel = SetListScreenViewModel(
            savedStateHandle = savedStateHandleRule.savedStateHandleMock,
            pokemonCardRepository = FakePokemonCardRepository()
        )
    }

    @Test
    fun `SetListUiState should be Loaded when view model inits with a NetworkResultSuccess`() =
        runTest {
            // When
            advanceUntilIdle()
            // Then
            assertEquals(
                SetListUiState.Loaded(
                    selectedCard = null,
                    data = listOf(fakePokemonCardUiData)
                ),
                viewModel.uiState.value
            )
        }

    @Test
    fun `SetListUiState should be Error when view model inits with a NetworkResultError`() =
        runTest {
            // Given
            viewModel = SetListScreenViewModel(
                savedStateHandle = savedStateHandleRule.savedStateHandleMock,
                pokemonCardRepository = FakePokemonCardRepository(
                    useCase = GetCardsUseCase.Error
                )
            )
            // When
            advanceUntilIdle()
            // Then
            assertTrue(viewModel.uiState.value is SetListUiState.Error)
            assertTrue((viewModel.uiState.value as SetListUiState.Error).message is UiString.DynamicString)
        }

    @Test
    fun `SetListUiState should be Error when view model inits with a NetworkResultException`() =
        runTest {
            // Given
            viewModel = SetListScreenViewModel(
                savedStateHandle = savedStateHandleRule.savedStateHandleMock,
                pokemonCardRepository = FakePokemonCardRepository(
                    useCase = GetCardsUseCase.Exception
                )
            )
            // When
            advanceUntilIdle()
            // Then
            assertTrue(viewModel.uiState.value is SetListUiState.Error)
            assertTrue((viewModel.uiState.value as SetListUiState.Error).message is UiString.StringResource)
        }

    @Test
    fun `SetListUiState should be Loaded when setSelectedCard is called`() = runTest {
        // Given
        advanceUntilIdle()
        val card = fakePokemonCardUiData

        // When
        viewModel.setSelectedCard(
            card = card
        )
        // Then
        assertEquals(
            SetListUiState.Loaded(
                selectedCard = card,
                data = listOf(fakePokemonCardUiData)
            ),
            viewModel.uiState.value
        )
    }

    @Test
    fun `SetListUiState should be Loaded when clearSelectedCard is called`() = runTest {
        // Given
        advanceUntilIdle()
        val card = fakePokemonCardUiData
        viewModel.setSelectedCard(
            card = card
        )
        // When
        viewModel.clearSelectedCard()
        // Then
        assertEquals(
            SetListUiState.Loaded(
                selectedCard = null,
                data = listOf(fakePokemonCardUiData)
            ),
            viewModel.uiState.value
        )
    }


}