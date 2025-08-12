package com.andrewhossam.opengeomock.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.andrewhossam.opengeomock.feature.map.MapScreen
import com.andrewhossam.opengeomock.feature.map.MapViewModel
import com.andrewhossam.opengeomock.navigation.AppDestination
import com.andrewhossam.opengeomock.navigation.NavigationAction
import com.andrewhossam.opengeomock.navigation.Navigator
import com.andrewhossam.opengeomock.util.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        val navController = rememberNavController()
        val navigator = koinInject<Navigator>()

        ObserveAsEvents(flow = navigator.navigationActions) { action ->
            when (action) {
                is NavigationAction.Navigate ->
                    navController.navigate(action.destination) {
                        action.navOptions(this)
                    }

                NavigationAction.NavigateUp -> navController.navigateUp()
            }
        }

        NavHost(
            navController = navController,
            startDestination = navigator.startDestination,
            modifier = Modifier.padding(innerPadding),
        ) {
            navigation<AppDestination.HomeGraph>(startDestination = AppDestination.MapScreen) {
                composable<AppDestination.MapScreen> {
                    val viewModel = koinViewModel<MapViewModel>()
                    MapScreen(viewModel = viewModel)
                }
            }
        }
    }
}
