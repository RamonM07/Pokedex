package com.pokemon.pokedex.presentation.viewModel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.pokedex.data.remote.model.getPokemon.PokemonState
import com.pokemon.pokedex.domain.usesCase.GetPokemonUsesCase
import com.pokemon.pokedex.domain.usesCase.RequestLocationPermissionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonUsesCase: GetPokemonUsesCase,
    private val requestLocationPermissionUseCase : RequestLocationPermissionUseCase,
) : ViewModel() {

    private val _pokemonState = MutableStateFlow(PokemonState())
    val pokemonState: StateFlow<PokemonState> = _pokemonState.asStateFlow()

    private val _locationState = MutableStateFlow<Location?>(null)
    //val locationState: StateFlow<Location?> = _locationState

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onPokemonFound() {
        viewModelScope.launch {
            _eventFlow.emit("¡Encontraste un Pokémon")
        }
    }

    fun observeLocation() {
        viewModelScope.launch {
            requestLocationPermissionUseCase()
                .distinctUntilChanged { old, new ->
                    Log.d("old:: ","${old.latitude}")
                    Log.d("old:: ","${old.longitude}")
                    Log.d("new:: ","${new.latitude}")
                    Log.d("new:: ","${new.longitude}")
                    old.distanceTo(new) < 9.9f
                }
                .collect { location ->
                    _locationState.value = location
                    getPokemon()
                    onPokemonFound()
                }
        }
    }

    init {
        getPokemon()
    }

    fun getPokemon() {
        _pokemonState.value = PokemonState(isLoading = true)
        viewModelScope.launch {
            val id = (1..1000).random()
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
                    _pokemonState.value = PokemonState(error = it.message)
                }
        }
    }
}