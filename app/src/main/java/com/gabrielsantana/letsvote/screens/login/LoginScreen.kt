@file:OptIn(ExperimentalMaterial3Api::class)

package com.gabrielsantana.letsvote.screens.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.gabrielsantana.letsvote.MyApp
import com.gabrielsantana.letsvote.ui.icons.filled.Google
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel()
) {

    val context = LocalContext.current

    val startAddAccountIntentLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {

        }

    val coroutineScope = rememberCoroutineScope()

    val credentialManager =  remember {
        CredentialManager.create(context)
    }

    val googleIdOption = remember {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it ->
            str + "%02x".format(it)
        }
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId("730556988466-8g2dcfmruo018mv05rgka4qn8fldijei.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .setNonce(hashedNonce)
        .build()
    }

    val request =  remember {
        GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }
    LoginContent(
        onLogin = {
            coroutineScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context,
                    )
                    viewModel.handleSignIn(result)
                } catch (e: GetCredentialException) {
                    print("")

                }
            }
        }
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
              modifier = Modifier.align(Alignment.Center),

          ) {
              Icon(Icons.Default.Google, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Enter with Google")
          }
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