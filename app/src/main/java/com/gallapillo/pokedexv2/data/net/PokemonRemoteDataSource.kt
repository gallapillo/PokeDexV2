package com.gallapillo.pokedexv2.data.net

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
) {
    fun getPagingPokemon() = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = { PokemonPagingDataSource(pokemonApi) }
    ).flow

    suspend fun getPokemonDetail(pokemonId: Int) = pokemonApi.getPokemonDetail(pokemonId)
}