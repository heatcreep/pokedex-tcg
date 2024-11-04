package com.aowen.pokedex.datastore

import com.aowen.pokedex.model.data.UserPreferencesData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferencesData>

    /**
     * Initialize the data store with default values.
     * This is meant to be called exactly once, when the app is first opened
     * to seed the data store with initial values.
     */
    suspend fun initDataStore()

    /**
     * Update the selected sets in the data store.
     * @param newSet The set to add to the selected sets
     */
    suspend fun updateSelectedSets(newSet: String)

    /**
     * Remove a selected set from the data store.
     * @param setToRemove The set to remove from the selected sets
     */
    suspend fun removeSelectedSet(setToRemove: String)
}