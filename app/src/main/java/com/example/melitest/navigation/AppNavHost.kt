package com.example.melitest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.melitest.domain.util.Constants.ARG_NAME
import com.example.melitest.domain.util.Constants.ARG_QUERY
import com.example.melitest.presentation.screens.components.results.productDetail.ProductDetailRoute
import com.example.melitest.presentation.screens.login.LoginScreen
import com.example.melitest.presentation.screens.results.ResultsScreen
import com.example.melitest.presentation.screens.splash.SplashScreen

@Composable
fun AppNavHost(
    nav: NavHostController,
    startDestination: String = Routes.Splash.route
) {
    val actions = remember(nav) { NavActions(nav) }

    NavHost(navController = nav, startDestination = startDestination) {

        composable(Routes.Splash.route) {
            SplashScreen(onFinished = { actions.toLogin(clearStack = true) })
        }

        composable(Routes.Login.route) {
            LoginScreen(
                onContinue = { name -> actions.toResults(name, "") },
                onNoUser   = { }
            )
        }

        composable(
            route = Routes.Results.route,
            arguments = Routes.Results.args
        ) { backStackEntry ->
            val name  = backStackEntry.arguments?.getString(ARG_NAME).orEmpty()
            val query = backStackEntry.arguments?.getString(ARG_QUERY).orEmpty()

            ResultsScreen(
                name = name,
                query = query,
                onSearch = { q -> actions.toResults(name, q) },
                onProductClick = { id -> actions.toDetail(id) }
            )
        }

        composable(
            route = Routes.Detail.routeWithArg,
            arguments = Routes.Detail.args
        ) {
            ProductDetailRoute(onBack = { actions.back() })
        }

        composable(Routes.Home.route) {
        }
    }
}