package com.turingalan.pokemon.ui.update

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingalan.pokemon.R
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.repository.PokemonRepository
import com.turingalan.pokemon.ui.detail.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonUpdateViewModel @Inject constructor(
    private val _repository: PokemonRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<UpdateUiState>(UpdateUiState.New)
    val uiState : StateFlow<UpdateUiState>
        get() = _uiState.asStateFlow()
    private var artwork:Int=0
    private var  sprite:Int=0

    val titleState = TextFieldState()
    val descriptionState = TextFieldState()

    fun getPokemonById(id: Int) {
        viewModelScope.launch {
            _repository.getPokemonByID(id)
                .catch { exception ->
                    _uiState.value = UpdateUiState.Error(exception.message ?: "Error desconocido")
                }
                .collect { pokemon ->
                    if (pokemon != null) {
                        artwork=pokemon.artworkId
                        sprite=pokemon.spriteId

                    } else {
                        _uiState.value = UpdateUiState.Error("Pokémon no encontrado con id: $id")
                    }
                }
        }

    }

    fun onCreate() = titleState.text.isNotEmpty() && descriptionState.text.isNotEmpty()
    fun create(pokemonId:Int){
        if (onCreate()){
            getPokemonById(pokemonId)
            viewModelScope.launch {
                _repository.updatePokemon(
                    Pokemon(
                        id = pokemonId,
                        name = titleState.text.toString(),
                        type = descriptionState.text.toString(),
                        spriteId = sprite,
                        artworkId = artwork
                    ), pokemonId
                )
                _uiState.value = UpdateUiState.Created
            }

        }else{
            _uiState.value = UpdateUiState.Error("Los campos no pueden estar vacios")
        }


    }
    fun cancel(){
        _uiState.value = UpdateUiState.Cancelled
    }

}
sealed class UpdateUiState(){
    object New: UpdateUiState()
    data class Error(val message: String): UpdateUiState()
    object Cancelled: UpdateUiState()
    object Created : UpdateUiState()

}