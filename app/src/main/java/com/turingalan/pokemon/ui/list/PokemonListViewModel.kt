package com.turingalan.pokemon.ui.list

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val _repository: PokemonRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<ListlUiState>(ListlUiState.New)

    val uiState: StateFlow<ListlUiState>
        get() = _uiState.asStateFlow()

    fun getAllPokemons() {
        viewModelScope.launch {
            _uiState.value = ListlUiState.New
            delay(1000)
            _repository.getAllPokemons()
                .catch { exception ->
                    _uiState.value = ListlUiState.Error(exception.message ?: "Error desconocido")
                }
                .collect { pokemonsLista ->
                    _uiState.value = ListlUiState.Loaded(pokemonsLista)
                }
        }
    }
}


sealed class ListlUiState(){
    object New: ListlUiState()
    data class Error(val message : String): ListlUiState()
    data class Loaded(val pokemons: List<Pokemon> ) : ListlUiState()
}
