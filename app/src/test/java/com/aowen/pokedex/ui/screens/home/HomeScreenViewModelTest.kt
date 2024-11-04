@file:OptIn(ExperimentalCoroutinesApi::class)
package com.aowen.pokedex.ui.screens.home

import com.aowen.pokedex.MainDispatcherRule
import com.aowen.pokedex.fakes.FakePokemonCardRepository
import com.aowen.pokedex.fakes.FakeUserPreferencesRepository
import com.aowen.pokedex.fakes.GetCardsUseCase
import com.aowen.pokedex.ui.util.UiString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeScreenViewModel

    private var fakeUserPreferencesRepository = FakeUserPreferencesRepository()

    private val fakeSingleSet = "Base"

    private val fakeAllSets = listOf(
        "Base",
        "Jungle",
        "Fossil",
    )

    @Before
    fun setup() {
        viewModel = HomeScreenViewModel(
            tcgPokemonCardRepository = FakePokemonCardRepository(),
            userPreferencesRepository = fakeUserPreferencesRepository
        )
    }

    @After
    fun tearDown() {
        // Reset the user preferences repository after each test
        fakeUserPreferencesRepository = FakeUserPreferencesRepository()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `HomeScreenViewModel should update state when initialized`() = runTest {
        advanceUntilIdle()
        // When
        val expected = HomeScreenUiState.Loaded(
            allSets = fakeAllSets,
            selectedSets = fakeAllSets
        )
        // Then
        assertEquals(
            expected,
            viewModel.uiState.value
        )
    }

    @Test
    fun `HomeScreenViewModel should update state with error message when network call fails`() =
        runTest {
            viewModel = HomeScreenViewModel(
                tcgPokemonCardRepository = FakePokemonCardRepository(
                    useCase = GetCardsUseCase.Error
                ),
                userPreferencesRepository = FakeUserPreferencesRepository()
            )
            advanceUntilIdle()
            // Then
            val actual = viewModel.uiState.value as HomeScreenUiState.Loaded
            assertEquals(emptyList<String>(), actual.allSets)
            assertEquals(fakeAllSets, actual.selectedSets)
            assertTrue(actual.errorMessage is UiString.StringResource)
        }

    @Test
    fun `HomeScreenViewModel should update state with error message when network call throws exception`() =
        runTest {
            viewModel = HomeScreenViewModel(
                tcgPokemonCardRepository = FakePokemonCardRepository(
                    useCase = GetCardsUseCase.Exception
                ),
                userPreferencesRepository = FakeUserPreferencesRepository()
            )
            advanceUntilIdle()
            // Then
            val actual = viewModel.uiState.value as HomeScreenUiState.Loaded
            assertEquals(emptyList<String>(), actual.allSets)
            assertEquals(fakeAllSets, actual.selectedSets)
            assertTrue(actual.errorMessage is UiString.StringResource)
        }

    @Test
    fun `onAddSelectedSet should update selected sets in user preferences repository`() = runTest {
        viewModel.onAddSelectedSet(fakeSingleSet)
        advanceUntilIdle()
        // Then
        assertEquals(
            fakeUserPreferencesRepository.updateSelectedSetsCounter.value,
            1
        )
    }

    @Test
    fun `onRemoveSelectedSet should update selected sets in user preferences repository`() =
        runTest {
            viewModel.onRemoveSelectedSet(fakeSingleSet)
            advanceUntilIdle()
            // Then
            assertEquals(
                fakeUserPreferencesRepository.updateSelectedSetsCounter.value,
                -1
            )
        }
}