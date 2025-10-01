package com.example.melitest.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.melitest.domain.util.Constants.ARG_ID
import com.example.melitest.domain.util.Constants.ARG_NAME
import com.example.melitest.domain.util.Constants.ARG_QUERY

sealed class Routes(val route: String) {

    data object Splash : Routes("splash")
    data object Login  : Routes("login")
    data object Home   : Routes("home")

    data object Results : Routes("results?name={name}&query={query}") {
        val args = listOf(
            navArgument(ARG_NAME) { type = NavType.Companion.StringType; defaultValue = "" },
            navArgument(ARG_QUERY) { type = NavType.Companion.StringType; defaultValue = "" }
        )
        fun build(name: String, query: String) = "results?name=${Uri.encode(name)}&query=${Uri.encode(query)}"
    }

    data object Detail : Routes("detail") {
        val routeWithArg = "$route/{$ARG_ID}"
        val args = listOf(navArgument(ARG_ID) { type = NavType.Companion.StringType })
        fun build(productId: String) = "$route/${Uri.encode(productId)}"
    }
}