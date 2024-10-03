package com.gabrielsantana.letsvote.screens

import android.content.res.Resources.Theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn

private const val TAG = "AppState"

@Stable
class AppState(
    coroutineScope: CoroutineScope
) {
    private val _themeMode = MutableStateFlow<ThemeMode>(ThemeMode.System)
    val themeMode = _themeMode.asStateFlow()

    private val _isDynamicColorsEnabled = MutableStateFlow(false)
    val isDynamicColorsEnabled = _isDynamicColorsEnabled.asStateFlow()

    val isLoggedIn = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        Firebase.auth.addAuthStateListener(authStateListener)
        awaitClose {
            Firebase.auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(), Firebase.auth.currentUser != null)


    fun setThemeMode(themeMode: ThemeMode) {
        _themeMode.value = themeMode
    }

    fun setDynamicColorsMode(enabled: Boolean) {
        _isDynamicColorsEnabled.value = enabled
    }

}

val AppState.isDarkMode: Boolean
    @Composable
    get() {
        val themeMode by themeMode.collectAsStateWithLifecycle()
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