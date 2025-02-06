package com.gabrielsantana.letsvote.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen() {
    var uiState by remember {
        mutableStateOf(
            HomeUiState(
                username = Firebase.auth.currentUser?.displayName
            )
        )
    }
    HomeContent(
        uiState = uiState,
        onLogout = {
            FirebaseAuth.getInstance().signOut()
        }
    )
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val displayMessage = if (uiState.username == null) {
            "We couldn't find your name. You can logout on tapping the button below."
        } else {
            "Hello, ${uiState.username}. You can logout on tapping the button below."
        }
        Text(displayMessage)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onLogout) { Text("Logout") }
    }
}