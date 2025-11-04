package com.turingalan.pokemon.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    object List: Route(route = "PokemonScreen")

    @Serializable
    object Form: Route(route = "PokemonForm")
    @Serializable
    object Update: Route(route = "PokemonUpdate")

    @Serializable
    data class Detail(val id: Int): Route(route = "PokemonDetail/{$id}")
}