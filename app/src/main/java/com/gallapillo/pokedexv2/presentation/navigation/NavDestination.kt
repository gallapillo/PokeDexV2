package com.gallapillo.pokedexv2.presentation.navigation

sealed class NavDestination(val route: String) {
    data object PokemonList : NavDestination("pokemonList")

    data object PokemonDetail : NavDestination("pokemonDetail/{pokemonId}")
}
