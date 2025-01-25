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

@Serializable
object NewPollScreen

@Serializable
object NewQuestionDialogScreen

@Composable
fun App(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState()
) {
    val navController = rememberNavController()
    val themeMode by appState.themeMode.collectAsStateWithLifecycle()
    val isDynamicColorsEnabled by appState.isDynamicColorsEnabled.collectAsStateWithLifecycle()
    val isSignedIn by appState.isLoggedIn.collectAsStateWithLifecycle()

    LetsVoteTheme(
        darkTheme = appState.isDarkMode,
        dynamicColor = isDynamicColorsEnabled
    ) {
        NavHost(
            navController = navController,
            startDestination = if (isSignedIn) HomeScreen else LoginScreen,
        ) {
            composable<LoginScreen> {
                LoginScreen(
                    onNavigateToHome = {

                    }
                )
            }
            dialog<SettingsDialogScreen> {
                SettingsScreen(
                    themeMode = themeMode,
                    onChangeThemeMode = {
                        appState.setThemeMode(it)
                    },
                    isDynamicColorsEnabled = isDynamicColorsEnabled,
                    onToggleDynamicColors = appState::setDynamicColorsMode,
                    onDismissRequest = {
                        navController.popBackStack()
                    }
                )
            }

        }

    }
}
