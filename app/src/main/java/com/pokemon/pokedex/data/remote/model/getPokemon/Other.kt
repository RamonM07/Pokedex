package com.pokemon.pokedex.data.remote.model.getPokemon


import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork? = OfficialArtwork()
)