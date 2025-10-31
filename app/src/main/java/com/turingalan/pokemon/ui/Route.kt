package com.turingalan.pokemon.ui

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    object List: Route(route = "PokemonScreen")

    @Serializable
    object Form: Route(route = "PokemonForm")


    @Serializable
    data class Detail(val id: Long): Route(route = "PokemonDetail/{$id}")
}