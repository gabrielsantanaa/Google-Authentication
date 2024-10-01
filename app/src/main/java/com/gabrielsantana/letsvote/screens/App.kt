package com.gabrielsantana.letsvote.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabrielsantana.letsvote.screens.home.HomeScreen
import com.gabrielsantana.letsvote.screens.login.LoginScreen
import com.gabrielsantana.letsvote.ui.theme.LetsVoteTheme
import kotlinx.serialization.Serializable


@Serializable
object LoginScreen

@Serializable
object HomeScreen

@Composable
fun App(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState()
) {
    val navController = rememberNavController()

    val isLoggedIn by appState.isLoggedIn.collectAsStateWithLifecycle()
    val themeMode by appState.themeMode.collectAsStateWithLifecycle()


    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(HomeScreen)
        } else {
            navController.navigate(LoginScreen)
        }
    }
    LetsVoteTheme(
        darkTheme = if (themeMode is ThemeMode.System) isSystemInDarkTheme() else themeMode is ThemeMode.Dark,
        dynamicColor = themeMode.useDynamicColor
    ) {
        NavHost(
            navController = navController,
            startDestination = LoginScreen
        ) {
            composable<LoginScreen> {
                LoginScreen()
            }
            composable<HomeScreen> {
                HomeScreen()
            }
        }

    }
}
