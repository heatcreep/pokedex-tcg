package com.aowen.pokedex.ui.screens.home.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aowen.pokedex.navigation.PokedexRoute
import com.aowen.pokedex.ui.screens.home.HomeScreenRoute

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable<PokedexRoute.HomeRoute>(
        enterTransition = {
            slideIntoContainer(SlideDirection.End)
        },
        exitTransition = {
            slideOutOfContainer(SlideDirection.Start)
        },
    ) {
        HomeScreenRoute(
            navController = navController
        )
    }
}

