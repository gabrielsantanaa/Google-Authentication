@file:OptIn(ExperimentalMaterial3Api::class)

package com.gabrielsantana.letsvote.screens.login

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabrielsantana.letsvote.ui.icons.filled.Google
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SideEffect {

    }
    LaunchedEffect(uiState.signRequest) {
        if (uiState.signRequest != null) {
            val result = CredentialManager.create(context).getCredential(context, uiState.signRequest!!)
            viewModel.handleSignResult(result)
        }
    }

    LoginContent(
        uiState = uiState,
        onLogin = viewModel::signWithGoogle,
        onSignOut = viewModel::signOut
    )
}


@Composable
fun LoginContent(
    uiState: LoginUiState,
    onLogin: () -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
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
            if (uiState.isUserSignedIn) {
                Button(
                    onClick = onSignOut,
                ) {
                    Text("Sign Out")
                }
            } else {
                Button(
                    onClick = onLogin,
                ) {
                    Icon(Icons.Default.Google, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Enter with Google")
                }
            }

            if (uiState.isError) {
                Text("An error occurred")
            }

        }
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
            onSignOut = { TODO() }
        )
    }
}