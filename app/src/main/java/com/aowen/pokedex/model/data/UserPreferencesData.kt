package com.aowen.pokedex.model.data

data class UserPreferencesData(
    val hasOpenedApp: Boolean = false,
    val selectedSets: List<String>,
)
