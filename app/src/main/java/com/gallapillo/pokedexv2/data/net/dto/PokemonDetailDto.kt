package com.gallapillo.pokedexv2.data.net.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<Type>,
    val stats: List<Stat>
)

@Serializable
data class Sprites(
    val front_default: String
)

@Serializable
data class Type(
    val type: TypeDetail
)

@Serializable
data class TypeDetail(
    val name: String
)

@Serializable
data class Stat(
    val base_stat: Int,
    val stat: StatDetail
)

@Serializable
data class StatDetail(
    val name: String
)
