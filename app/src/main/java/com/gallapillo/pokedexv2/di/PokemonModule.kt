package com.gallapillo.pokedexv2.di

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy
import com.gallapillo.pokedexv2.data.local.PokemonDao
import com.gallapillo.pokedexv2.data.local.PokemonDatabase
import com.gallapillo.pokedexv2.data.local.PokemonLocalDataSource
import com.gallapillo.pokedexv2.data.net.PokemonApi
import com.gallapillo.pokedexv2.util.Url.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesJson() = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }


    @Singleton
    @Provides
    fun provideContentType() = "application/json".toMediaType()

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        contentType: MediaType
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesPokemonApi(retrofit: Retrofit): PokemonApi = retrofit.create(PokemonApi::class.java)

    @Provides
    fun provideApplicationContext(app: Application): Context = app.applicationContext

    @Provides
    fun providePokemonDatabase(applicationContext: Context) = PokemonDatabase(applicationContext)

    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase) = pokemonDatabase.pokemonDao()

    @Provides
    fun providePokemonLocalDataSource(pokemonDao: PokemonDao) = PokemonLocalDataSource(pokemonDao)

    @Provides
    fun provideImageLoader(applicationContext: Context) = ImageLoader.Builder(applicationContext)
        .allowHardware(true)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .build()
}