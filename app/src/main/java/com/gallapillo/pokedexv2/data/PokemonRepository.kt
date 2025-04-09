package com.gallapillo.pokedexv2.data

import android.util.Log
import androidx.paging.PagingData
import com.gallapillo.pokedexv2.data.local.PokemonEntity
import com.gallapillo.pokedexv2.data.local.PokemonLocalDataSource
import com.gallapillo.pokedexv2.data.net.PokemonRemoteDataSource
import com.gallapillo.pokedexv2.domain.mapper.mapPagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) {
    fun getPagingPokemon(): Flow<PagingData<PokemonEntity>> {
        return try {
            val data = remoteDataSource.getPagingPokemon().mapPagingData { entity ->
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.insertPokemon(entity)
                }
            }
            data
        } catch (e: Exception) {
            Log.e(PokemonRepository::class.java.simpleName, "Error: ${e.localizedMessage}")
            localDataSource.getPagingPokemon()
        }
    }

    suspend fun getPokemonDetail(pokemonId: Int) = remoteDataSource.getPokemonDetail(pokemonId)
}
