package com.gallapillo.pokedexv2.data.net.dto

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)