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
class PokemonCreateViewModel @Inject constructor(
    private val _repository: PokemonRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<CreateUiState>(CreateUiState.New)
    val uiState : StateFlow<CreateUiState>
        get() = _uiState.asStateFlow()

    val titleState = TextFieldState()
    val descriptionState = TextFieldState()
    fun generateId():Int{
        val num = _repository.getLastId()
        return num+1
    }

    fun onCreate() = titleState.text.isNotEmpty() && descriptionState.text.isNotEmpty()
    fun create(){
        if (onCreate()){
            viewModelScope.launch {
                _repository.addPokemon(
                    Pokemon(
                        id = generateId(),
                        name = titleState.text.toString(),
                        type = descriptionState.text.toString(),
                        spriteId = R.drawable.sprite_39,
                        artworkId = R.drawable.artwork_39
                    )
                )
                _uiState.value = CreateUiState.Created
            }

        }else{
            _uiState.value = CreateUiState.Error("Los campos no pueden estar vacios")
        }


    }
    fun cancel(){
        _uiState.value = CreateUiState.Cancelled
    }

}
sealed class CreateUiState(){
    object New: CreateUiState()
    data class Error(val message: String): CreateUiState()
    object Cancelled: CreateUiState()
    object Created : CreateUiState()

}