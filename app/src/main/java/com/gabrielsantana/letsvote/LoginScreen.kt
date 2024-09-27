@file:OptIn(ExperimentalMaterial3Api::class)

package com.gabrielsantana.letsvote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen() {
    LoginContent(
        onLogin = {  }
    )
}

@Composable
fun LoginContent(
    onLogin: () -> Unit,
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
      Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
      ) {
          Button(
              onClick = onLogin,
              modifier = Modifier.align(Alignment.Center)
          ) { Text("Enter with Google") }
      }
    }
}

@Preview
@Composable
fun LoginContentPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        LoginContent(
            onLogin = {  },
            modifier = modifier
        )
    }
}