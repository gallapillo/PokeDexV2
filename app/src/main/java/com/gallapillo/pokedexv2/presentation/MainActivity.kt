package com.gallapillo.pokedexv2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.gallapillo.pokedexv2.presentation.detail.PokemonDetailScreen
import com.gallapillo.pokedexv2.presentation.list.PokemonListScreen
import com.gallapillo.pokedexv2.presentation.theme.PokeDexV2Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val pokemonViewModel: PokemonViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeDexV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph()
                }
            }
        }
    }

    @Composable
    fun NavigationGraph() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "pokemonList") {
            composable("pokemonList") {
                PokemonListScreen(
                    pokemonViewModel,
                    navController,
                    imageLoader
                )
            }
            composable("pokemonDetail/{pokemonId}") { backStackEntry ->
                val pokemonId = backStackEntry.arguments?.getString("pokemonId")
                PokemonDetailScreen(
                    pokemonId = pokemonId,
                    viewModel = pokemonViewModel,
                    imageLoader = imageLoader
                )
            }
        }
    }

}
