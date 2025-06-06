package com.pokemon.pokedex.domain.repository

import com.pokemon.pokedex.data.remote.model.getPokemon.GetPokemonRs

interface PokemonRepository {
    suspend fun getPokemonById(id: Int): GetPokemonRs?
}