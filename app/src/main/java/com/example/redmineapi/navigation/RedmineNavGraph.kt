package com.example.redmineapi.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.redmineapi.AppViewModelProvider
import com.example.redmineapi.screen.DetailIssueScreen
import com.example.redmineapi.screen.DetailDestination
import com.example.redmineapi.screen.HomeDestination
import com.example.redmineapi.screen.HomeScreen
import com.example.redmineapi.screen.HomeScreenViewModel

@Composable
fun RedmineNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            Log.d("TAG", "NavHost")
            val homeScreenViewModel: HomeScreenViewModel =
                viewModel(factory = AppViewModelProvider.Factory)

            HomeScreen(
                navigateToDetail = { navController.navigate("${DetailDestination.route}/$it") },
                retryAction = homeScreenViewModel::getTasks
            )
        }
        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.issueIdArg)
            {
                type = NavType.IntType
            })
        ) {
            DetailIssueScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}