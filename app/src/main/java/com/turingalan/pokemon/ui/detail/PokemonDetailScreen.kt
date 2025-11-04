package com.turingalan.pokemon.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import com.turingalan.pokemon.data.model.Pokemon
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turingalan.pokemon.ui.create.CreateUiState
import com.turingalan.pokemon.ui.create.TodoCreateForm


@Composable
fun PokeomDetail(
    pokemonId: Int,
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onNavegationBack: () -> Unit,
    onUpdate: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()



    when(uiState){

        is DetailUiState.New -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            viewModel.getPokemonById(pokemonId)
        }
        is DetailUiState.Loaded -> {
            val pokemon = (uiState as DetailUiState.Loaded).pokemon

            PokemonDetailScreen(
                pokemon = pokemon,
                onNavegationBack = onNavegationBack,
                onUpdate = onUpdate
               )
        }
        is DetailUiState.Error -> {
            val errorMessage = (uiState as DetailUiState.Error).message
            PokemonDetailScreen(
                error = errorMessage,
                onNavegationBack = onNavegationBack,

            )
        }
    }
}




@Composable
fun PokemonDetailScreen(
    pokemon: Pokemon?=null,
    error : String?=null,
    onNavegationBack: () -> Unit,
    onUpdate : () -> Unit?={}

    ) {
    if(pokemon!=null){
    Scaffold { paddingValues ->

            Card(
                modifier = Modifier
                    .padding(top = 60.dp, start = 8.dp, end = 8.dp)
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = pokemon.artworkId),
                        contentDescription = pokemon.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tipo: ${pokemon.type}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onUpdate()
                        }
                    ) {
                        Text("Actualizar")
                    }
                    Button(
                        onClick = {
                            onNavegationBack()
                        }
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }else {
        Text( modifier = Modifier
            .padding(top = 90.dp),
            text = error!!)
        Button(
            onClick = {
                onNavegationBack()
            }
        ) {
            Text("Volver")
        }
    }
}
