package com.pokemon.pokedex.data.remote.repository

import com.pokemon.pokedex.data.PokemonApiService
import com.pokemon.pokedex.data.getPokemon.GetPokemonRs
import com.pokemon.pokedex.domain.repository.PokemonRepository

class PokemonRepositoryImplement(
    private val api: PokemonApiService
) : PokemonRepository {
    override suspend fun getPokemonById(id: Int): GetPokemonRs? {
        return api.getPokemonById(id = id).body()
    }
}