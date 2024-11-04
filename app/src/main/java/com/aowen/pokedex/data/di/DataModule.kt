package com.aowen.pokedex.data.di

import com.aowen.pokedex.data.PokemonCardRepository
import com.aowen.pokedex.data.TcgPokemonCardRepository
import com.aowen.pokedex.network.PokemonTcgApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesTcgPokemonCardRepository(
        pokemonTcgApiService: PokemonTcgApiService
    ): PokemonCardRepository {
        return TcgPokemonCardRepository(pokemonTcgApiService)
    }
}