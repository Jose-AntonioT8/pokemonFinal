package com.turingalan.pokemon.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.turingalan.pokemon.R
import com.turingalan.pokemon.data.model.Pokemon
import com.turingalan.pokemon.data.repository.PokemonRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.flow

class PokemonInMemoryRepository @Inject constructor(): PokemonRepository {

    val pokemonList: MutableList<Pokemon> = mutableListOf(
        Pokemon(
            id = 1,
            name = "Bulbasaur",
            type = "Planta/Veneno",
            spriteId = R.drawable.sprite_1,
            artworkId = R.drawable.artwork_1
        ),
        Pokemon(
            id = 4,
            name = "Charmander",
            type = "Fuego",
            spriteId = R.drawable.sprite_4,
            artworkId = R.drawable.artwork_4
        ),
        Pokemon(
            id = 7,
            name = "Squirtle",
            type = "Agua",
            spriteId = R.drawable.sprite_7,
            artworkId = R.drawable.artwork_7
        ),
        Pokemon(
            id = 10,
            name = "Caterpie",
            type = "Bicho",
            spriteId = R.drawable.sprite_10,
            artworkId = R.drawable.artwork_10
        ),
        Pokemon(
            id = 25,
            name = "Pikachu",
            type = "Eléctrico",
            spriteId = R.drawable.sprite_25,
            artworkId = R.drawable.artwork_25
        ),
        Pokemon(
            id = 39,
            name = "JigglyPuff",
            type = "Normal/Hada",
            spriteId = R.drawable.sprite_39,
            artworkId = R.drawable.artwork_39
        ),
        Pokemon(
            id = 133,
            name = "Eeve",
            type = "Normal",
            spriteId = R.drawable.sprite_133,
            artworkId = R.drawable.artwork_133
        ),
        Pokemon(
            id = 143,
            name = "Snorlax",
            type = "Normal",
            spriteId = R.drawable.sprite_143,
            artworkId = R.drawable.artwork_143
        ),
        Pokemon(
            id = 144,
            name = "Paco",
            type = "Subnormal",
            spriteId = R.drawable.sprite_143,
            artworkId = R.drawable.artwork_143
        )

    )

    override fun getAllPokemons(): Flow<List<Pokemon>> {
        return flow {
            emit(pokemonList)
        }
    }

    override fun getPokemonByID(id: Int): Flow<Pokemon?> {
        return flow {
            emit(pokemonList.find { it.id == id })
        }
    }
    override fun addPokemon(pokemon: Pokemon) {
        pokemonList.add(pokemon)
    }

    override fun getLastId(): Int {
        return pokemonList.last().id
    }
}//ver si cuando se crean dos pokemos sus ids son diferentes

