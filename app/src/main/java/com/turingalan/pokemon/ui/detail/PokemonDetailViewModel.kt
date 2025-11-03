package com.turingalan.pokemon.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.turingalan.pokemon.R
import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val _repository: PokemonRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.New)

    val uiState: StateFlow<DetailUiState>
        get() = _uiState.asStateFlow()

    fun getPokemonById(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.New
            delay(1000)
            _repository.getPokemonByID(id)
                .catch { exception ->
                    _uiState.value = DetailUiState.Error(exception.message ?: "Error desconocido")
                }
                .collect { pokemon ->
                    if (pokemon != null) {
                        _uiState.value = DetailUiState.Loaded(pokemon)
                    } else {
                        _uiState.value = DetailUiState.Error("Pokémon no encontrado con id: $id")
                    }
                }
        }

    }

    }


sealed class DetailUiState(){
    object New: DetailUiState()
    data class Error(val message : String): DetailUiState()
    data class Loaded(val pokemon: Pokemon) : DetailUiState()
}
