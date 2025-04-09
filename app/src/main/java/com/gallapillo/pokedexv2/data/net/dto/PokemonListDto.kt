package com.gallapillo.pokedexv2.data.net.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListDto(
    val count: Int,
    val next: String,
    val results: List<Pokemon>
)