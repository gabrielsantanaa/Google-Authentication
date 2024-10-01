package com.gabrielsantana.letsvote.screens

import android.content.res.Resources.Theme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

class AppState(
    coroutineScope: CoroutineScope
) {

    private val _themeMode = MutableStateFlow<ThemeMode>(ThemeMode.System(false))
    val themeMode = _themeMode.asStateFlow()

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

sealed class ThemeMode {

    abstract val useDynamicColor: Boolean

    data class System(override val useDynamicColor: Boolean) : ThemeMode()
    data class Light(override val useDynamicColor: Boolean) : ThemeMode()
    data class Dark(override val useDynamicColor: Boolean) : ThemeMode()
}

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState = remember {
    AppState(
        coroutineScope = coroutineScope,
    )
}