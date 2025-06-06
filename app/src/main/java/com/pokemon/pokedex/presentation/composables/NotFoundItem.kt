package com.pokemon.pokedex.presentation.composables

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pokemon.pokedex.R

@Composable
fun NotFoundItem(
    onButtonClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(stringResource(R.string.message_pokemon_not_found))
        Button(
            onClick = {
                onButtonClick()
            }) {
            Text(stringResource(R.string.message_retry))
        }
    }
}