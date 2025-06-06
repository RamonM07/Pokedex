package com.pokemon.pokedex.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pokemon.pokedex.domain.usesCase.GetPokemonUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel
@Inject
constructor(
    private val getPokemonUsesCase: GetPokemonUsesCase,
) : ViewModel() {


    init {
        getCharacters()
    }

    private fun getCharacters() {
        /*viewModelScope.launch {
            getPokemonUsesCase
                .execute()
                .onSuccess { result ->

                    Log.d(
                        "RESULT:: ",
                        "${
                            result
                        }",
                    )
                }.onFailure {
                    Log.d("RESULT:: ", "${it.message}")
                }
        }*/
    }
}