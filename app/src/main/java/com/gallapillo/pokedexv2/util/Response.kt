package com.gallapillo.pokedexv2.util

sealed interface Response<out D, out E> {
    data object Loading : Response<Nothing, Nothing>

    data class Success<out D>(val data: D) : Response<D, Nothing>

    data class Error<out E>(val error: E) : Response<Nothing, E>
}
