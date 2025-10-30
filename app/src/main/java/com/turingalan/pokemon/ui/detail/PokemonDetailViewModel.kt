package com.turingalan.pokemon.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val _repository: PokemonRepository
): ViewModel() {
    fun getPokemonById (id: Int): StateFlow<Pokemon?> =
        _repository.getPokemonByID(id)
            .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
