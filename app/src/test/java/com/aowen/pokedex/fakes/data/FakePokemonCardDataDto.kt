package com.aowen.pokedex.fakes.data

import com.aowen.pokedex.network.model.CardMarketDto
import com.aowen.pokedex.network.model.ImageDto
import com.aowen.pokedex.network.model.PokemonCardDataDto
import com.aowen.pokedex.network.model.PokemonCardsDataDto
import com.aowen.pokedex.network.model.PokemonCardDto
import com.aowen.pokedex.network.model.PricesDto

val fakePokemonCardDto = PokemonCardDto(
    id = "testId",
    name = "Pikachu",
    supertype = "Pokémon",
    images = ImageDto(
        small = "https://images.pokemontcg.io/base4/104.png",
        large = "https://images.pokemontcg.io/base4/104_hires.png"
    ),
    cardMarket = CardMarketDto(
        prices = PricesDto(
            averageSellPrice = 1.00,
        )
    ),
    artist = "Real Artist",
    flavorText = "This is a test flavor text",
    rarity = "Common"
)

val fakeSuccessfulPokemonCardsDataDto: PokemonCardsDataDto =
    PokemonCardsDataDto(
        data = listOf(
            fakePokemonCardDto
        )
    )

val fakeSuccessfulPokemonCardDataDto: PokemonCardDataDto =
    PokemonCardDataDto(
        data = fakePokemonCardDto
    )
