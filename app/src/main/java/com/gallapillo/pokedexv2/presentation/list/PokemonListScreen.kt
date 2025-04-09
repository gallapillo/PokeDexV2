package com.gallapillo.pokedexv2.presentation.list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gallapillo.pokedexv2.data.local.PokemonEntity
import com.gallapillo.pokedexv2.presentation.PokemonViewModel
import com.gallapillo.pokedexv2.presentation.theme.Purple80


@Composable
fun PokemonListScreen(
    pokemonViewModel: PokemonViewModel,
    navController: NavHostController,
    imageLoader: ImageLoader
) {
    val data = pokemonViewModel.getPagingAllPokemon().collectAsLazyPagingItems()
    val onClick: (Int) -> Unit = remember { { navController.navigate("pokemonDetail/$it") } }

    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
        items(data.itemCount) { index ->
            val pokemon = data[index]
            ItemRow(
                pokemon = pokemon,
                imageIndex = index + 1,
                imageLoader = imageLoader,
                onClick = onClick
            )
        }
    }
}

@Composable
fun ItemRow(
    pokemon: PokemonEntity?,
    imageIndex: Int,
    imageLoader: ImageLoader,
    onClick: (Int) -> Unit
) {
    val pokemonName = pokemon?.name ?: "Unknown"
    val imageUrl =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$imageIndex.png"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onClick(imageIndex) }
            .border(1.dp, color = Purple80)
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = pokemonName,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .build(),
            imageLoader = imageLoader,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
    }
}
