package com.gallapillo.pokedexv2.presentation.detail

import com.gallapillo.pokedexv2.data.net.dto.PokemonDetailDto

sealed interface PokemonDetailState {
    data object Idle : PokemonDetailState
    data object Loading : PokemonDetailState
    data class Success(val pokemonDetailDto: PokemonDetailDto) : PokemonDetailState
}