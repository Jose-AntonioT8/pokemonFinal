package com.turingalan.pokemon

import PokemonListScreen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.turingalan.pokemon.ui.Route
import com.turingalan.pokemon.ui.create.TodoCreateScreen
import com.turingalan.pokemon.ui.detail.PokemonDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val startDestination = Route.List.route // Usamos el string de la ruta

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Pokemons") }
            )
        },//habria que cambiar el bottom bar para que solo aparezca el boton en detailScreen y ListScreen pasandole una funcion para que se redirija a pokemon form
        bottomBar ={
            Button(onClick = { navController.navigate("PokemonForm") }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        val contentModifier = Modifier
            .consumeWindowInsets(innerPadding)
            .padding()

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            // Pantalla de la lista
            composable(Route.List.route) {
                PokemonListScreen(
                    modifier = contentModifier,
                    onShowDetail = { id ->
                        navController.navigate("PokemonDetail/$id")
                    }
                )
            }
            composable(route = "PokemonForm") {
                TodoCreateScreen(
                    modifier = contentModifier,
                    onNavegationBack = {
                        navController.navigate(Route.List.route)
                    }
                )
            }


            // Pantalla de detalle
            composable(
                route = "PokemonDetail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id") ?: 0L
                PokemonDetailScreen(
                    modifier = contentModifier,
                    pokemonId = id.toInt(),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
