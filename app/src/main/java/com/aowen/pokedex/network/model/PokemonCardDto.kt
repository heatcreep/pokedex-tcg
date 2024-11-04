package com.aowen.pokedex.network.model

import com.aowen.pokedex.data.PokemonCardUiData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.NumberFormat
import java.util.Locale

@Serializable
data class PricesDto(
    val averageSellPrice: Double
)

@Serializable
data class CardMarketDto(
    val prices: PricesDto
)

@Serializable
data class PokemonCardDto(
    val id: String,
    val name: String,
    val supertype: String,
    val images: ImageDto,
    @SerialName("cardmarket")
    val cardMarket: CardMarketDto,
    val artist: String,
    val flavorText: String?,
    val rarity: String
)

@Serializable
data class PokemonCardsDataDto(
    val data: List<PokemonCardDto>
)

@Serializable
data class PokemonCardDataDto(
    val data: PokemonCardDto
)

fun PokemonCardDto.toPokemonCardUiData(): PokemonCardUiData {

    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val cardMarketPrice = currencyFormat.format(cardMarket.prices.averageSellPrice)
    return PokemonCardUiData(
        id = id,
        name = name,
        supertype = supertype,
        imageUrl = images.large,
        cardMarketPrice = cardMarketPrice,
        artist = artist,
        flavorText = flavorText,
        rarity = rarity
    )
}
