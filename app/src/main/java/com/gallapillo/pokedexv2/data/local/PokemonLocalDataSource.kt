package com.gallapillo.pokedexv2.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val dao: PokemonDao
) {
    fun insertAllPokemon(pokemon: List<PokemonEntity>) {
        dao.insertAll(pokemon)
    }

    fun getPagingPokemon(): Flow<PagingData<PokemonEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { dao.getAllPokemonPaging() }
        ).flow
    }

    fun getListPokemon() = dao.getAllPokemon()

    fun insertPokemon(entity: PokemonEntity) {
        dao.insert(entity)
    }
}