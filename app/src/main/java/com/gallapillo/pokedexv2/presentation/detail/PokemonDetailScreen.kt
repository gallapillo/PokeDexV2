package com.gallapillo.pokedexv2.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gallapillo.pokedexv2.data.net.dto.PokemonDetailDto
import com.gallapillo.pokedexv2.presentation.PokemonViewModel

@Composable
fun PokemonDetailScreen(viewModel: PokemonViewModel, pokemonId: String?, imageLoader: ImageLoader) {
    val id = pokemonId?.toInt() ?: 1

    // Запускаем получение данных только один раз при первом рендере
    LaunchedEffect(id) {
        viewModel.getPokemonDetail(pokemonId = id)
    }

    val state = viewModel.pokemonDetailState.collectAsState()

    // Обработка состояния с использованием when
    when (val pokemonDetailState = state.value) {
        is PokemonDetailState.Loading -> {
            LoadingIndicator()
        }

        is PokemonDetailState.Success -> {
            PokemonDetailColumn(
                pokemonDetailDto = pokemonDetailState.pokemonDetailDto,
                pokemonId = id,
                imageLoader = imageLoader
            )
        }

        is PokemonDetailState.Idle -> {
            // Обработка ошибки, если необходимо
            ErrorScreen()
        }
    }
}

@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen() {}

@Composable
fun PokemonDetailColumn(
    pokemonDetailDto: PokemonDetailDto,
    pokemonId: Int,
    imageLoader: ImageLoader
) {
    val url =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonId}.png"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            contentDescription = null,
            imageLoader = imageLoader,
            modifier = Modifier.size(256.dp)
        )

        Text(text = pokemonDetailDto.name)
        Text(text = "Weight: ${pokemonDetailDto.weight}")
        Text(text = "Height: ${pokemonDetailDto.height}")
        Text(text = "Types: ${pokemonDetailDto.types.joinToString { it.type.name }}")
        Text(text = "Stats: \n${pokemonDetailDto.stats.joinToString(separator = "") { "${it.stat.name} - ${it.base_stat}\n" }}")
    }
}