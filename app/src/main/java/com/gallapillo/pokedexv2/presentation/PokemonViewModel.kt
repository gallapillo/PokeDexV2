package com.gallapillo.pokedexv2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gallapillo.pokedexv2.domain.use_case.GetListPokemonUseCase
import com.gallapillo.pokedexv2.domain.use_case.GetPokemonDetailUseCase
import com.gallapillo.pokedexv2.presentation.list.PokemonDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getListPokemonUseCase: GetListPokemonUseCase,
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase
): ViewModel() {
    private val _pokemonDetailState: MutableStateFlow<PokemonDetailState> = MutableStateFlow(
        PokemonDetailState.Idle)
    val pokemonDetailState = _pokemonDetailState.asStateFlow()

    fun getPagingAllPokemon() = getListPokemonUseCase()

    fun getPokemonDetail(pokemonId: Int) {
        _pokemonDetailState.value = PokemonDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
           val res = getPokemonDetailUseCase(pokemonId)
            _pokemonDetailState.value = PokemonDetailState.Success(res)
        }
    }
}