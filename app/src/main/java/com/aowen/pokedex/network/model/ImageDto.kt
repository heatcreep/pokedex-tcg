package com.aowen.pokedex.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val small: String,
    val large: String
)
