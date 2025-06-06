package com.pokemon.pokedex.domain.repository

import com.pokemon.pokedex.data.getPokemon.GetPokemonRs

interface PokemonRepository {
    suspend fun getPokemonById(id: Int): GetPokemonRs?
}