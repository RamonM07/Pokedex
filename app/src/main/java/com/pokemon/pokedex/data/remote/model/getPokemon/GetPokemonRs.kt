package com.pokemon.pokedex.data.remote.model.getPokemon


import com.google.gson.annotations.SerializedName

data class GetPokemonRs(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("sprites")
    val sprites: Sprites? = Sprites(),
    @SerializedName("types")
    val types: List<Type>? = listOf()
)