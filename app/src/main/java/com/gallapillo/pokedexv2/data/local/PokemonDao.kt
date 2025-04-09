package com.gallapillo.pokedexv2.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemonPaging(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon_table")
    fun getAllPokemon(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonList: List<PokemonEntity>)

    @Query("DELETE FROM pokemon_table")
    fun deleteAll()
}