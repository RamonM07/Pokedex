package com.pokemon.pokedex.data.remote.model.getPokemon


import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String? = "",
    @SerializedName("other")
    val other: Other? = Other()
)