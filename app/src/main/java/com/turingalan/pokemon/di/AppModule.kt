package com.turingalan.pokemon.di

import com.turingalan.pokemon.data.repository.PokemonInMemoryRepository
import com.turingalan.pokemon.data.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Este módulo se encarga de decirle a Hilt cómo inyectar la implementación del repositorio.
// @Module indica que esta clase es un módulo de dependencias de Hilt.
// @InstallIn define el alcance: en este caso, SingletonComponent para toda la vida de la aplicación.
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    // @Binds indica a Hilt que para inyectar la interfaz PokemonRepository,
    // debe usar la clase PokemonInMemoryRepository como su implementación.
    // Es más eficiente que @Provides porque no necesita crear métodos instanciadores, solo vincula una clase ya creada por @Inject constructor.
    // El metodo es abstracto porque le indica a Hilt la vinculación, pero no necesita lógica interna.
    // @Singleton asegura que la instancia de PokemonInMemoryRepository será única en toda la app.
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(repository: PokemonInMemoryRepository): PokemonRepository
}
