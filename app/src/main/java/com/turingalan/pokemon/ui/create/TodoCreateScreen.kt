package com.turingalan.pokemon.ui.create

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
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun TodoCreateScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonCreateViewModel = hiltViewModel(),
    onNavegationBack: () -> Unit,
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState){
        CreateUiState.Cancelled -> {
            onNavegationBack()
        }
        is CreateUiState.Created -> {
            onNavegationBack()
        }
        is CreateUiState.New -> {
            TodoCreateForm(modifier = modifier,
                viewModel = viewModel)
        }
        is CreateUiState.Error -> {
            val errorMessage = (uiState as CreateUiState.Error).message
            TodoCreateForm(modifier = modifier,
                viewModel = viewModel,
                error = errorMessage)
        }
    }
}

@Composable
fun TodoCreateForm(
    viewModel : PokemonCreateViewModel,
    modifier: Modifier = Modifier,
    error:String?=null
){

    Surface(modifier = modifier) {

        val isScreenIsInError = error!=null


        Column(modifier = Modifier.padding(8.dp, top=80.dp)) {

            OutlinedTextField(
                state = viewModel.titleState,
                isError = isScreenIsInError
            )
            OutlinedTextField(
                state = viewModel.descriptionState,
                isError = isScreenIsInError

            )

            Button(
                onClick = {
                    viewModel.create()
                }
            ){
                Text("Crear")
            }
            Button(
                onClick = { viewModel.cancel() }
            ){
                Text("Cancelar")
            }
        }
    }
}
