package com.gallapillo.pokedexv2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.gallapillo.pokedexv2.presentation.PokemonViewModel
import com.gallapillo.pokedexv2.presentation.detail.PokemonDetailScreen
import com.gallapillo.pokedexv2.presentation.list.PokemonListScreen

@Composable
fun PokemonNavigationGraph(pokemonViewModel: PokemonViewModel, imageLoader: ImageLoader) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavDestination.PokemonList.route
    ) {
        composable(NavDestination.PokemonList.route) {
            PokemonListScreen(
                pokemonViewModel = pokemonViewModel,
                navController = navController,
                imageLoader = imageLoader
            )
        }
        composable(NavDestination.PokemonDetail.route) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getString("pokemonId")
            PokemonDetailScreen(
                pokemonId = pokemonId,
                viewModel = pokemonViewModel,
                imageLoader = imageLoader
            )
        }
    }
}