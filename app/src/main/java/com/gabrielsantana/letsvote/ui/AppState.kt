package com.gabrielsantana.letsvote.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn

private const val TAG = "AppState"

@Stable
class AppState(
    coroutineScope: CoroutineScope
) {
    var themeMode by mutableStateOf<ThemeMode>(ThemeMode.System)

    var isDynamicColorsEnabled by mutableStateOf(false)

    val isLoggedIn = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        Firebase.auth.addAuthStateListener(authStateListener)
        awaitClose {
            Firebase.auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), Firebase.auth.currentUser != null)

}

val AppState.isDarkMode: Boolean
    @Composable
    get() {
        return if (themeMode is ThemeMode.System) isSystemInDarkTheme() else themeMode is ThemeMode.Dark
    }

sealed class ThemeMode {
    data object System : ThemeMode()
    data object Light : ThemeMode()
    data object Dark : ThemeMode()
}

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember {
    AppState(
        coroutineScope = coroutineScope,
    )
}