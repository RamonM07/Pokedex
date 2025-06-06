package com.pokemon.pokedex.presentation.viewModel

import android.location.Location
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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonUsesCase: GetPokemonUsesCase,
    private val requestLocationPermissionUseCase : RequestLocationPermissionUseCase,
) : ViewModel() {

    private val _pokemonState = MutableStateFlow(PokemonState())
    val pokemonState: StateFlow<PokemonState> = _pokemonState.asStateFlow()

    private var lastLocation : Location? = null

    private val _showAlert = MutableSharedFlow<Unit>()
    val showAlert = _showAlert.asSharedFlow()

    private fun onPokemonFound() {
        viewModelScope.launch {
            _showAlert.emit(Unit)
        }
    }

    fun onLocationPermissionGranted() {
        observeLocation()
    }

    private fun observeLocation() {
        viewModelScope.launch {
            requestLocationPermissionUseCase()
                .filterNotNull()
                .collect { newLocation ->
                    if (lastLocation != null) {
                        getPokemon()
                        onPokemonFound()
                    } else {
                        lastLocation = newLocation
                    }
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
                }.onFailure {
                    _pokemonState.value = PokemonState(error = it.message)
                }
        }
    }
}