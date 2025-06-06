package com.pokemon.pokedex.data.remote.model.getPokemon


import com.google.gson.annotations.SerializedName

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String? = ""
)