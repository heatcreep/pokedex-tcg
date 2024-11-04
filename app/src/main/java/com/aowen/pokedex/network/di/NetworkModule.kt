package com.aowen.pokedex.network.di

import com.aowen.pokedex.BuildConfig
import com.aowen.pokedex.network.PokemonTcgApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private fun okHttpClient() = OkHttpClient().newBuilder()
        .addInterceptor(
            Interceptor { chain ->
                val request: Request = chain.request()
                    .newBuilder()
                    .addHeader("X-Api-Key", BuildConfig.POKEMON_TCG_API_KEY)
                    .build()
                chain.proceed(request)
            }
        )

    @Provides
    @Singleton
    fun providesRetrofit(json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/")
            .client(okHttpClient().build())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType())
            )
            .build()

    @Provides
    @Singleton
    fun providesPokemonTcgApiService(retrofit: Retrofit): PokemonTcgApiService =
        retrofit.create(PokemonTcgApiService::class.java)
}