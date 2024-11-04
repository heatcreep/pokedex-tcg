package com.aowen.pokedex.fakes

import com.aowen.pokedex.datastore.UserPreferencesRepository
import com.aowen.pokedex.model.data.UserPreferencesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

class FakeUserPreferencesRepository(
    private val fakeUserPreferencesData: UserPreferencesData = UserPreferencesData(
        hasOpenedApp = true,
        selectedSets = listOf(
            "Base",
            "Jungle",
            "Fossil",
        ),
    )
) : UserPreferencesRepository {

    private val _initCounter = MutableStateFlow(0)
    private val _updateSelectedSetsCounter = MutableStateFlow(0)

    val initCounter: StateFlow<Int> = _initCounter
    val updateSelectedSetsCounter: StateFlow<Int> = _updateSelectedSetsCounter

    override val userPreferences: Flow<UserPreferencesData> = flowOf(
        fakeUserPreferencesData
    )

    override suspend fun initDataStore() {
        _initCounter.update {
            it + 1
        }
    }

    override suspend fun updateSelectedSets(newSet: String) {
        _updateSelectedSetsCounter.update {
            it + 1
        }
    }

    override suspend fun removeSelectedSet(setToRemove: String) {
        _updateSelectedSetsCounter.update {
            it - 1
        }
    }
}