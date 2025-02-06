package com.gabrielsantana.letsvote.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.gabrielsantana.letsvote.home.HomeScreen
import com.gabrielsantana.letsvote.login.LoginScreen
import com.gabrielsantana.letsvote.settings.SettingsScreen
import com.gabrielsantana.letsvote.ui.theme.LetsVoteTheme
import kotlinx.serialization.Serializable


@Serializable
object LoginScreen

@Serializable
object HomeScreen

@Serializable
object SettingsDialogScreen

@Composable
fun App(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState()
) {
    val navController = rememberNavController()
    val isSignedIn by appState.isLoggedIn.collectAsStateWithLifecycle()

    LetsVoteTheme(
        darkTheme = appState.isDarkMode,
        dynamicColor = appState.isDynamicColorsEnabled
    ) {
        NavHost(
            navController = navController,
            startDestination = if (isSignedIn) HomeScreen else LoginScreen,
            modifier = modifier
        ) {
            composable<LoginScreen> {
                LoginScreen(
                    onNavigateToHome = {
                        //don't need to navigate manually
                    }
                )
            }
            composable<HomeScreen> {
                HomeScreen()
            }
            dialog<SettingsDialogScreen> {
                SettingsScreen(
                    themeMode = appState.themeMode,
                    onChangeThemeMode = {
                        appState.themeMode = it
                    },
                    isDynamicColorsEnabled = appState.isDynamicColorsEnabled,
                    onToggleDynamicColors = { appState.isDynamicColorsEnabled = it },
                    onDismissRequest = {
                        navController.popBackStack()
                    }
                )
            }

        }

    }
}
