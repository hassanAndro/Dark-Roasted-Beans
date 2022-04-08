package com.example.coffeeit.ui.main

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.coffeeit.R
import com.example.coffeeit.ui.compose.CoffeeExtras
import com.example.coffeeit.ui.compose.CoffeeSize
import com.google.accompanist.insets.ProvideWindowInsets
import com.example.coffeeit.ui.compose.CoffeeStyle
import com.example.coffeeit.util.ConnectionState
import com.example.coffeeit.util.connectivityState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun CoffeeMachineMainScreen() {
    ConnectivityStatus()
    val navController = rememberAnimatedNavController()

    ProvideWindowInsets {
        AnimatedNavHost(
            navController = navController,
            startDestination = NavScreen.CoffeeStyle.route
        ) {
            composable(
                NavScreen.CoffeeStyle.route,
                enterTransition = { slideInVertically(initialOffsetY = { 5000 }) },
                exitTransition = { fadeOut(animationSpec = tween(50)) }
            ) {
                CoffeeStyle(
                    viewModel = hiltViewModel(),
                    selectTypeId = {
                        navController.navigate("${NavScreen.CoffeeSize.route}/$it")
                    }
                )
            }
            composable(
                route = NavScreen.CoffeeSize.routeWithArgument,
                enterTransition = { slideInVertically(initialOffsetY = { 5000 }) },
                exitTransition = { fadeOut(animationSpec = tween(50)) },
                arguments = listOf(
                    navArgument(NavScreen.CoffeeSize.argument0) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val typeId = backStackEntry.arguments?.getString(NavScreen.CoffeeSize.argument0)
                    ?: return@composable

                CoffeeSize(
                    coffeeTypeId = typeId,
                    viewModel = hiltViewModel(),
                    selectTypeId = {
                        navController.navigate("${NavScreen.CoffeeExtras.route}/$it")
                    }
                ) {
                    navController.navigateUp()
                }
            }
            composable(
                route = NavScreen.CoffeeExtras.routeWithArgument,
                enterTransition = { slideInVertically(initialOffsetY = { 5000 }) },
                exitTransition = { fadeOut(animationSpec = tween(50)) },
                arguments = listOf(
                    navArgument(NavScreen.CoffeeExtras.argument0) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val typeId = backStackEntry.arguments?.getString(NavScreen.CoffeeExtras.argument0)
                    ?: return@composable

                CoffeeExtras(
                    coffeeTypeId = typeId,
                    viewModel = hiltViewModel(),
                    /*selectTypeId = {
                        navController.navigate("${NavScreen.CoffeeExtras.route}/$it")
                    }*/
                ) {
                    navController.navigateUp()
                }
            }
        }
    }
}

sealed class NavScreen(val route: String) {
    object CoffeeStyle : NavScreen("CoffeeStyle")

    object CoffeeSize : NavScreen("CoffeeSize") {
        const val routeWithArgument: String = "CoffeeSize/{coffeeTypeId}"
        const val argument0: String = "coffeeTypeId"
    }

    object CoffeeExtras : NavScreen("CoffeeExtras") {
        const val routeWithArgument: String = "CoffeeExtras/{coffeeTypeId}"
        const val argument0: String = "coffeeTypeId"
    }
}

@ExperimentalCoroutinesApi
@Composable
fun ConnectivityStatus() {
    // This will cause re-composition on every network state change
    val connection by connectivityState()
    val context = LocalContext.current

    val isConnected = connection === ConnectionState.Available
    when {
        isConnected -> {
            Toast.makeText(context, R.string.network_available, Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(context, R.string.no_network, Toast.LENGTH_SHORT).show()
        }
    }
}
