package com.aowen.pokedex.ui.screens.setlist.navigation

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.aowen.pokedex.navigation.PokedexRoute
import com.aowen.pokedex.ui.screens.setlist.SetListScreenRoute


fun NavController.navigateToSetList(setName: String) {
    this.navigate(PokedexRoute.SetListRoute(setName))
}

fun NavGraphBuilder.setListScreen(navController: NavController) {
    composable<PokedexRoute.SetListRoute>(
        enterTransition = {
            slideIntoContainer(SlideDirection.Start)
        },
        exitTransition = {
            slideOutOfContainer(SlideDirection.End)
        },
    ) {
        SetListScreenRoute(
            navController = navController
        )
    }
}