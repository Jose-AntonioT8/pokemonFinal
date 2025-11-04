package com.turingalan.pokemon.ui.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.turingalan.pokemon.ui.update.UpdateUiState
import com.turingalan.pokemon.ui.update.PokemonUpdateViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PokeomUpdate(
    modifier: Modifier = Modifier,
    viewModel: PokemonUpdateViewModel = hiltViewModel(),
    onNavegationBack: () -> Unit,
    pokemonId : Int
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState){
        UpdateUiState.Cancelled -> {
            onNavegationBack()
        }
        is UpdateUiState.Created -> {
            onNavegationBack()
        }
        is UpdateUiState.New -> {
            PokeomUpdateForm(modifier = modifier,
                pokemonId = pokemonId,
                viewModel = viewModel)
        }
        is UpdateUiState.Error -> {
            val errorMessage = (uiState as UpdateUiState.Error).message
            PokeomUpdateForm(modifier = modifier,
                viewModel = viewModel,

                error = errorMessage)
        }
    }
}

@Composable
fun PokeomUpdateForm(
    viewModel : PokemonUpdateViewModel,
    modifier: Modifier = Modifier,
    error:String?=null,
    pokemonId : Int?=null

    ){

    Surface(modifier = modifier) {

        val isScreenIsInError = error!=null


        Column(modifier = Modifier.padding(8.dp, top=100.dp)) {

            OutlinedTextField(
                state = viewModel.titleState,
                isError = isScreenIsInError
            )
            Text(text = error ?: "")
            OutlinedTextField(
                state = viewModel.descriptionState,
                isError = isScreenIsInError

            )
            Text(text = error ?: "")


            Button(
                onClick = {
                    viewModel.create(pokemonId!!)
                }
            ){
                Text("Actualizar")
            }
            Button(
                onClick = { viewModel.cancel() }
            ){
                Text("Cancelar")
            }
        }
    }
}
