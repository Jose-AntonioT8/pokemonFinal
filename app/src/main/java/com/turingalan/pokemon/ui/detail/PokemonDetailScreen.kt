package com.turingalan.pokemon.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.turingalan.pokemon.data.model.Pokemon
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokemonId: Int,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onBack: () -> Unit = {} // Usado si quieres añadir retroceso
) {
    // Recogemos el Pokémon reactivo vía StateFlow. Valor inicial nulo por seguridad de tipos
    val pokemon: Pokemon? by viewModel.getPokemonById(pokemonId).collectAsState(initial = null)

    Scaffold { paddingValues ->
        if (pokemon != null) {
            Card(
                modifier = Modifier
                    .padding( top =60.dp, start = 8.dp, end = 8.dp)
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = pokemon!!.artworkId),
                        contentDescription = pokemon!!.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = pokemon!!.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tipo: ${pokemon!!.type}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Text(
                modifier = Modifier.padding(32.dp),
                text = "Pokemon no encontrado",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
