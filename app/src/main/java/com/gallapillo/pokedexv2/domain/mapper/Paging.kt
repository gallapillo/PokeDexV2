package com.gallapillo.pokedexv2.domain.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.gallapillo.pokedexv2.data.local.PokemonEntity
import com.gallapillo.pokedexv2.data.net.dto.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<PagingData<Pokemon>>.mapPagingData(
    onMap: (PokemonEntity) -> Unit
): Flow<PagingData<PokemonEntity>> {
    return map { pagingData ->
        pagingData.map { pokemon ->
            val entity = PokemonEntity(
                name = pokemon.name,
                url = pokemon.url
            )
            onMap(entity)
            entity
        }
    }
}