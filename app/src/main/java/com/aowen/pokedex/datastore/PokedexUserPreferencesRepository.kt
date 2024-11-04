package com.aowen.pokedex.datastore

import androidx.datastore.core.DataStore
import com.aowen.pokedex.model.data.UserPreferencesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PokedexUserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) : UserPreferencesRepository {

    override val userPreferences: Flow<UserPreferencesData> =
        dataStore.data.map {
            UserPreferencesData(
                hasOpenedApp = it.hasOpenedApp,
                selectedSets = it.selectedSetsMap.keys.toList()
            )
        }

    override suspend fun initDataStore() {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .putAllSelectedSets(
                    mapOf(
                        "Base" to true,
                        "Jungle" to true,
                        "Fossil" to true,
                    )
                )
                .setHasOpenedApp(true)
                .build()

        }
    }

    override suspend fun updateSelectedSets(newSet: String) {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .putSelectedSets(newSet, true)
                .build()
        }
    }

    override suspend fun removeSelectedSet(setToRemove: String) {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .removeSelectedSets(setToRemove)
                .build()
        }
    }
}