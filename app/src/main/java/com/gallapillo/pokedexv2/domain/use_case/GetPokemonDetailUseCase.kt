package com.gallapillo.pokedexv2.domain.use_case

import com.gallapillo.pokedexv2.data.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int) = repository.getPokemonDetail(pokemonId)
}