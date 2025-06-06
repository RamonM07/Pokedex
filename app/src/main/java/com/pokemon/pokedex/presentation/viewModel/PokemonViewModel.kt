package com.pokemon.pokedex.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.pokedex.data.getPokemon.PokemonState
import com.pokemon.pokedex.domain.usesCase.GetPokemonUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonUsesCase: GetPokemonUsesCase
) : ViewModel() {

    private val _pokemonState = MutableStateFlow(PokemonState())
    val pokemonState: StateFlow<PokemonState> = _pokemonState.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        _pokemonState.value = PokemonState(isLoading = true)
        viewModelScope.launch {
            val id = (1..151).random()
            getPokemonUsesCase
                .execute(id)
                .onSuccess { result ->
                    if (result != null){
                        _pokemonState.value = PokemonState(pokemon = result)
                    } else {
                        _pokemonState.value = PokemonState(error = "Empty Result")
                    }
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