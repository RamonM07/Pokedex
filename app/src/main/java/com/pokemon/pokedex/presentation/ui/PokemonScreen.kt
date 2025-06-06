package com.pokemon.pokedex.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pokemon.pokedex.presentation.composables.PokemonItem
import com.pokemon.pokedex.presentation.theme.PokedexTheme
import com.pokemon.pokedex.presentation.viewModel.PokemonViewModel

@Composable
fun PokemonScreen(
    title: String,
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val pokemonState by viewModel.pokemonState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = title,
            modifier = modifier
        )
        when {
            pokemonState.isLoading -> {
                CircularProgressIndicator()
            }

            pokemonState.error?.isNotEmpty() == true -> {
                Text("Error in Service")
            }

            pokemonState.pokemon.name?.isNotEmpty() == true -> {
                PokemonItem(
                    pokemon = pokemonState.pokemon,
                    onButtonClick = {
                        viewModel.getPokemon()
                    },
                    onSelectItem = {}
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexTheme {
        PokemonScreen("Pokedex")
    }
}