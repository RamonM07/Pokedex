package com.pokemon.pokedex.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.pokedex.domain.usesCase.GetPokemonUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonUsesCase: GetPokemonUsesCase
) : ViewModel() {


    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val id = (1..151).random()
            getPokemonUsesCase
                .execute(id)
                .onSuccess { result ->

                    Log.d(
                        "RESULT:: ",
                        "$result",
                    )
                }.onFailure {
                    Log.d("RESULT:: ", "${it.message}")
                }
        }
    }
}