package com.gallapillo.pokedexv2.data.net

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gallapillo.pokedexv2.data.net.dto.Pokemon
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingDataSource(
    private val pokemonApi: PokemonApi
) : PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val page = params.key ?: 0
            val limit = 30

            val response = pokemonApi.getPokemonsPaging(limit, page).results

            val nextKey = if (response.isEmpty()) null else page + limit
            val prevKey = if (page == 0) null else page - limit

            LoadResult.Page(
                data = response,
                nextKey = nextKey,
                prevKey = prevKey
            )
        } catch (e: HttpException) {
            Log.e(PokemonPagingDataSource::class.java.simpleName, "HTTP Error: ${e.message()}", e)
            LoadResult.Error(NetworkError("Network error occurred: ${e.message()}"))
        } catch (e: IOException) {
            Log.e(PokemonPagingDataSource::class.java.simpleName, "IO Error: ${e.localizedMessage}", e)
            LoadResult.Error(NetworkError("IO error occurred: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e(PokemonPagingDataSource::class.java.simpleName, "Unexpected Error: ${e.localizedMessage}", e)
            LoadResult.Error(UnknownError("An unexpected error occurred: ${e.localizedMessage}"))
        }
    }
}
