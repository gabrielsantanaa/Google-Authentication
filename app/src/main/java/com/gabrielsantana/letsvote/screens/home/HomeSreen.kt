@file:OptIn(ExperimentalMaterial3Api::class)

package com.gabrielsantana.letsvote.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        onLogout = viewModel::logout,
        onSettings = onNavigateToSettings,
        onRoomCodeChange = viewModel::updateRoomCode,
        modifier = modifier
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onRoomCodeChange: (String) -> Unit,
    onLogout: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    //TODO check if whether is good keep this here or to hoist it to the viewmodel
    var showSignOutAlert by remember { mutableStateOf(false) }

    if (showSignOutAlert) {
        SignOutAlertDialog(
            onDismissRequest = { showSignOutAlert = showSignOutAlert.not() },
            onSignOut = {
                showSignOutAlert = showSignOutAlert.not()
                onLogout()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = {
                        showSignOutAlert = showSignOutAlert.not()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Default.Logout,
                            contentDescription = "Logout the user"
                        )
                    }
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Open app settings")
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = uiState.roomCode,
                onValueChange = onRoomCodeChange,
                label = {
                    Text("Room Code")
                },
                supportingText = {
                    Text(
                        text = "${uiState.roomCode.length}/$MAX_ROOM_CODE_LENGTH",
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) { Text("Join room") }
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) { Text("Create room") }
        }
    }
}

@Composable
fun SignOutAlertDialog(
    onDismissRequest: () -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { TextButton(onClick = onSignOut) { Text("Sign out") } },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("Cancel") } },
        title = { Text("Sign out from the app?") },
        text = { Text("You'll not be able to join any room and use the app.") },
        icon = { Icon(Icons.AutoMirrored.Default.Logout, null) },
        modifier = modifier
    )
}

@Preview
@Composable
private fun WarningLogoutDialogPreview() {
    MaterialTheme {
        SignOutAlertDialog(onDismissRequest = {}, {})
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeUiState("123456"),
            onRoomCodeChange = {},
            onLogout = {},
            onSettings = {}
        )
    }
}