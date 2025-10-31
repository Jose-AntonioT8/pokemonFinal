package com.turingalan.pokemon.ui.create

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingalan.pokemon.R
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val _repository: PokemonRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<CreateUiState>
    = MutableStateFlow(CreateUiState.New)
    val uiState : StateFlow<CreateUiState>
        get() = _uiState.asStateFlow()

    val titleState = TextFieldState()
    val descriptionState = TextFieldState()

    fun onCreate() = titleState.text.isNotEmpty() && descriptionState.text.isNotEmpty()
    fun create(){
        viewModelScope.launch {
            _repository.addPokemon(
                Pokemon(
                    id = 39,
                    name = titleState.text.toString(),
                    type = descriptionState.text.toString(),
                    spriteId = R.drawable.sprite_39,
                    artworkId = R.drawable.artwork_39
                )
            )
            _uiState.value = CreateUiState.Created//creo q no es correcto
        }

    }

}
sealed class CreateUiState(){
    object New: CreateUiState()
    data class Error(val message: String): CreateUiState()
    object Cancelled: CreateUiState()
    object Created : CreateUiState()

}