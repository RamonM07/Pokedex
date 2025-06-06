package com.pokemon.pokedex.domain.usesCase

import com.pokemon.pokedex.data.getPokemon.GetPokemonRs
import com.pokemon.pokedex.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUsesCase
@Inject
constructor(
    private val pokemonRepository: PokemonRepository,
) {

    suspend fun execute(id : Int): Result<GetPokemonRs?> =
        try {
            val data = pokemonRepository.getPokemonById(id)
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
}