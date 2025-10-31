package com.turingalan.pokemon.ui.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.turingalan.pokemon.ui.create.TodoCreateViewModel

@Composable
fun TodoCreateScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoCreateViewModel = hiltViewModel(),
){
    val _uiState by viewModel.uiState.collectAsState()


}