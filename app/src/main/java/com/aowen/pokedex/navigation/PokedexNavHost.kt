package com.aowen.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.aowen.pokedex.ui.screens.home.navigation.homeScreen
import com.aowen.pokedex.ui.screens.pokemondetails.navigation.pokemonCardDetailsScreen
import com.aowen.pokedex.ui.screens.setlist.navigation.setListScreen

@Composable
fun PokedexNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: PokedexRoute = PokedexRoute.HomeRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(navController = navController)
        setListScreen(navController = navController)
        pokemonCardDetailsScreen(navController = navController)
    }
}