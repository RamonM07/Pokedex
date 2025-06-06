package com.pokemon.pokedex.data.getPokemon


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("type")
    val type: TypeX? = TypeX()
)