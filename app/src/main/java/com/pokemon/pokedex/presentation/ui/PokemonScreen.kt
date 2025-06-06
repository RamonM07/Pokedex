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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pokemon.pokedex.R
import com.pokemon.pokedex.presentation.composables.NotFoundItem
import com.pokemon.pokedex.presentation.composables.PokemonItem
import com.pokemon.pokedex.presentation.viewModel.PokemonViewModel

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel
) {
    val pokemonState by viewModel.pokemonState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge.copy(
                shadow = Shadow(
                    color = MaterialTheme.colorScheme.tertiary,
                    offset = Offset(4f, 4f),
                    blurRadius = 4f
                )
            ),
            modifier = modifier
        )
        when {
            pokemonState.isLoading -> {
                CircularProgressIndicator()
            }

            pokemonState.error?.isNotEmpty() == true -> {
                NotFoundItem {
                    viewModel.getPokemon()
                }
            }

            pokemonState.pokemon.name?.isNotEmpty() == true -> {
                PokemonItem(
                    pokemon = pokemonState.pokemon,
                    onButtonClick = {
                        viewModel.getPokemon()
                    }
                )
            }
        }
    }

}