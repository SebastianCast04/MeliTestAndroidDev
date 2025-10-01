package com.example.melitest.navigation

import androidx.navigation.NavController

class NavActions(private val nav: NavController) {

    fun toLogin(clearStack: Boolean = true) {
        nav.navigate(Routes.Login.route) {
            if (clearStack) popUpTo(0)
            launchSingleTop = true
        }
    }

    fun toResults(name: String, query: String) {
        nav.navigate(Routes.Results.build(name, query)) {
            popUpTo(Routes.Results.route) { inclusive = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun toDetail(productId: String) {
        nav.navigate(Routes.Detail.build(productId))
    }

    fun back() { nav.popBackStack() }
}