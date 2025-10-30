package com.turingalan.pokemon.data.repository

import androidx.lifecycle.SavedStateHandle
import com.turingalan.pokemon.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    //Devolvemos un Flow que contendra una lista de Pokemons
    fun getAllPokemons(): Flow<List<Pokemon>>

    //Devolvemos un único Pokemon. En caso de no encontrar el ID, devuelve un nulo
    fun getPokemonByID(id: Int): Flow<Pokemon?>
}
