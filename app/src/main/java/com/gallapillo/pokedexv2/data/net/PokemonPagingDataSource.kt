package com.gallapillo.pokedexv2.data.net

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gallapillo.pokedexv2.data.net.dto.Pokemon

class PokemonPagingDataSource(
    private val pokemonApi: PokemonApi
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        // Возвращаем позицию, на которой произошел сбой
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            // Используем 0 как начальный offset
            val page = params.key ?: 0 // Начинаем с 0
            val limit = 30

            // Запрашиваем покемонов с API
            val response = pokemonApi.getPokemonsPaging(limit, page).results

            // Определяем ключи для следующей и предыдущей страниц
            val nextKey = if (response.isEmpty()) null else page + limit // Для следующей страницы
            val prevKey = if (page == 0) null else page - limit // Для предыдущей страницы

            LoadResult.Page(
                data = response,
                nextKey = nextKey,
                prevKey = prevKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
