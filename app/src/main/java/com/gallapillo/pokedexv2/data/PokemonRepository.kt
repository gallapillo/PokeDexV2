package com.gallapillo.pokedexv2.data

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.gallapillo.pokedexv2.data.local.PokemonEntity
import com.gallapillo.pokedexv2.data.local.PokemonLocalDataSource
import com.gallapillo.pokedexv2.data.net.PokemonRemoteDataSource
import com.gallapillo.pokedexv2.data.net.dto.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val localDataSource: PokemonLocalDataSource
) {
    fun getPagingPokemon(): Flow<PagingData<PokemonEntity>> {
        return try {
            // Получаем данные из удаленного источника
            val pagingData = mapPagingData(remoteDataSource.getPagingPokemon())

            // save to pagigng data
            savePagingDataToLocal(pagingData)

            pagingData
        } catch (e: Exception) {
            // Если произошла ошибка, возвращаем данные из локальной базы данных
            Log.e(PokemonRepository::class.java.simpleName, "Error: ${e.localizedMessage}")
            localDataSource.getPagingPokemon()
        }
    }

    fun mapToPokemonEntity(pokemon: Pokemon) = PokemonEntity(
        name = pokemon.name,
        url = pokemon.url
    )

    private fun savePagingDataToLocal(pagingData: Flow<PagingData<PokemonEntity>>) {
        CoroutineScope(Dispatchers.IO).launch {
            pagingData.collect { pagingData ->
                // Преобразуем PagingData в список PokemonEntity
                val pokemonList = mutableListOf<PokemonEntity>()
                // Сохраняем в локальную базу данных
                localDataSource.insertAllPokemon(pokemonList)
            }
        }
    }


    fun mapPagingData(
        sourceFlow: Flow<PagingData<Pokemon>>
    ): Flow<PagingData<PokemonEntity>> {
        return sourceFlow.map { pagingData ->
            pagingData.map { pokemon ->
                val entity = PokemonEntity(
                    name = pokemon.name,
                    url = pokemon.url
                )
                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.insertPokemon(entity)
                }
                entity
            }
        }
    }


    suspend fun getPokemonDetail(pokemonId: Int) = remoteDataSource.getPokemonDetail(pokemonId)
}
