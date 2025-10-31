package com.turingalan.pokemon.ui.create

import androidx.lifecycle.ViewModel
import com.turingalan.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val _repository: PokemonRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<CreateUiState>
    = MutableStateFlow(CreateUiState.New)
    val _uiState : StateFlow<CreateUiState>
        get() = _uiState.asStateFlow()

    val pokemons = _repository.getAllPokemons()

}
sealed class CreateUiState(){
    object New: CreateUiState()
    object Cancelled: CreateUiState()
    object Created : CreateUiState()

}