package com.pokemon.pokedex.data.remote.model.getPokemon


data class PokemonState(
    val isLoading: Boolean = false,
    val pokemon: GetPokemonRs = GetPokemonRs(),
    val error: String? = null
)