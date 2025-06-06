package com.pokemon.pokedex.data

import com.pokemon.pokedex.data.remote.model.getPokemon.GetPokemonRs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ):Response<GetPokemonRs>
}