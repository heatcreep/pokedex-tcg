package com.aowen.pokedex.ui.screens.pokemondetails.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aowen.pokedex.navigation.PokedexRoute
import com.aowen.pokedex.ui.screens.pokemondetails.PokemonCardDetailsRoute

fun NavController.navigateToPokemonCardDetails(cardId: String) {
    this.navigate(PokedexRoute.PokemonCardDetailsRoute(cardId = cardId))
}

fun NavGraphBuilder.pokemonCardDetailsScreen(navController: NavController) {
    composable<PokedexRoute.PokemonCardDetailsRoute>(
        enterTransition = {
            slideIntoContainer(SlideDirection.Start)
        },
        exitTransition = {
            slideOutOfContainer(SlideDirection.End)
        },
    ) {
        PokemonCardDetailsRoute(
            navController = navController,
            viewModel = hiltViewModel(),
        )
    }
}