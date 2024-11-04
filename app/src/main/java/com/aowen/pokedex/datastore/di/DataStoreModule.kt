package com.aowen.pokedex.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.aowen.pokedex.datastore.PokedexUserPreferencesRepository
import com.aowen.pokedex.datastore.UserPreferences
import com.aowen.pokedex.datastore.UserPreferencesRepository
import com.aowen.pokedex.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            produceFile = { context.dataStoreFile("user_preferences.pb") }
        )

    }

    @Provides
    @Singleton
    fun providesUserPreferencesRepository(
        dataStore: DataStore<UserPreferences>
    ): UserPreferencesRepository {
        return PokedexUserPreferencesRepository(dataStore)
    }
}