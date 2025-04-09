package com.gallapillo.pokedexv2.data.net

import com.gallapillo.pokedexv2.data.net.dto.PokemonDetailDto
import com.gallapillo.pokedexv2.data.net.dto.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonsPaging(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListDto

    @GET("pokemon/{pokemon_id}/")
    suspend fun getPokemonDetail(@Path("pokemon_id") pokemonId: Int): PokemonDetailDto
}