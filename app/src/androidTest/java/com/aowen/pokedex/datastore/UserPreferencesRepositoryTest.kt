package com.aowen.pokedex.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

const val TEST_DATASTORE_FILENAME = "test_user_preferences.pb"
const val TEST_DATASTORE_SET_ENTRY = "Bulbasaur"

private val testContext: Context = ApplicationProvider.getApplicationContext()

private val dispatcher = StandardTestDispatcher()
private val testScope = TestScope(dispatcher)

@RunWith(AndroidJUnit4::class)
class UserPreferencesRepositoryTest {

    private lateinit var testDataStore: DataStore<UserPreferences>
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @Before
    fun setup() {
        testDataStore = DataStoreFactory.create(
            serializer = UserPreferencesSerializer(),
            produceFile = { testContext.dataStoreFile(TEST_DATASTORE_FILENAME) }
        )
        userPreferencesRepository = PokedexUserPreferencesRepository(testDataStore)
    }

    @Test
    fun initDataStore_prepopulates_datastore() = runTest {
        testScope.launch {
            userPreferencesRepository.initDataStore()
            val userPreferencesData = userPreferencesRepository.userPreferences.first()
            assert(userPreferencesData.hasOpenedApp)
            assert(userPreferencesData.selectedSets.isNotEmpty())
        }
    }

    @Test
    fun updateSelectedSets_adds_new_set() = runTest {
        testScope.launch {
            userPreferencesRepository.initDataStore()
            userPreferencesRepository.updateSelectedSets(TEST_DATASTORE_SET_ENTRY)
            val userPreferencesData = userPreferencesRepository.userPreferences.first()
            assert(userPreferencesData.selectedSets.contains(TEST_DATASTORE_SET_ENTRY))
        }
    }

    @Test
    fun removeSelectedSet_removes_set() = runTest {
        testScope.launch {
            userPreferencesRepository.initDataStore()
            userPreferencesRepository.updateSelectedSets(TEST_DATASTORE_SET_ENTRY)
            userPreferencesRepository.removeSelectedSet(TEST_DATASTORE_SET_ENTRY)
            val userPreferencesData = userPreferencesRepository.userPreferences.first()
            assert(!userPreferencesData.selectedSets.contains(TEST_DATASTORE_SET_ENTRY))
        }
    }

    @After
    fun cleanup() {
        File(testContext.filesDir, "datastore").deleteRecursively()
    }
}