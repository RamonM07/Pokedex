package com.pokemon.pokedex.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pokemon.pokedex.R
import com.pokemon.pokedex.data.remote.model.getPokemon.GetPokemonRs

@Composable
fun PokemonItem(
    pokemon: GetPokemonRs,
    onButtonClick: () -> Unit,
    onSelectItem: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onSelectItem
    ) {
        AsyncImage(
            model = pokemon.sprites?.other?.officialArtwork?.frontDefault ?: "",
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

    }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = (pokemon.name ?: "").replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
    Button(
        onClick = {
            onButtonClick()
        }) {
        Text(stringResource(R.string.title_button_see_pokemon))
    }
}