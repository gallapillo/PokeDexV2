package com.gallapillo.pokedexv2.domain.use_case

import com.gallapillo.pokedexv2.data.PokemonRepository
import com.gallapillo.pokedexv2.data.net.PokemonRemoteDataSource
import javax.inject.Inject

class GetListPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke() = repository.getPagingPokemon()
}