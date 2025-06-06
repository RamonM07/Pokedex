package com.pokemon.pokedex.data.remote.model.getPokemon


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("type")
    val type: TypeX? = TypeX()
)