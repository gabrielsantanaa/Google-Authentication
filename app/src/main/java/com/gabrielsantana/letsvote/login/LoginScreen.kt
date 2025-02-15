@file:OptIn(ExperimentalMaterial3Api::class)

package com.gabrielsantana.letsvote.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabrielsantana.letsvote.ui.icons.filled.Google

private const val TAG = "LoginScreen.kt"

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isUserSignedIn) {
        if (uiState.isUserSignedIn) {
            onNavigateToHome()
        }
    }

    LoginContent(
        uiState = uiState,
        onLogin = viewModel::signWithGoogle,
        onLoginResult = viewModel::handleSignResult
    )
}


@Composable
fun LoginContent(
    uiState: LoginUiState,
    onLogin: () -> Unit,
    onLoginResult: (GetCredentialResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.signRequest) {
        if (uiState.signRequest != null) {
            try {
                val result =
                    CredentialManager.create(context).getCredential(context, uiState.signRequest)
                onLoginResult(result)
            } catch (e: GetCredentialCancellationException) {
                val result = snackbarHostState.showSnackbar(
                    message = "Google Sign-in cancelled",
                    actionLabel = "Retry",
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed) {
                    onLogin()
                }
                Log.e(TAG, "Login Error", e)
            } catch (e: NoCredentialException) {
                snackbarHostState.showSnackbar(
                    message = "There's no Google Account on this device",
                    duration = SnackbarDuration.Short
                )
                Log.e(TAG, "Login Error", e)
            }
            //catch this exception on end
            catch (e: GetCredentialException) {
                val result = snackbarHostState.showSnackbar(
                    message = "An error occurred",
                    actionLabel = "Retry",
                    duration = SnackbarDuration.Short
                )
                if (result == SnackbarResult.ActionPerformed) {
                    onLogin()
                }
                Log.e(TAG, "Login Error", e)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text("Login") }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onLogin,
            ) {
                Icon(Icons.Default.Google, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enter with Google")
            }

            if (uiState.isError) {
                Text("An error occurred")
            }

        }
    }
}

@Preview
@Composable
fun NewPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        LoginContent(
            onLogin = { },
            modifier = modifier,
            uiState = LoginUiState(isError = true),
            onLoginResult = {}
        )
    }
}


@Preview
@Composable
fun LoginContentPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        LoginContent(
            onLogin = { },
            modifier = modifier,
            uiState = LoginUiState(isUserSignedIn = false),
            onLoginResult = {}
        )
    }
}