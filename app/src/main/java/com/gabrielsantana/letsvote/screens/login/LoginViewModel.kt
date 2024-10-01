package com.gabrielsantana.letsvote.screens.login

import android.content.Context
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielsantana.letsvote.R
import com.gabrielsantana.letsvote.utils.generateNonce
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    val uiState = MutableStateFlow(LoginUiState())


    fun signWithGoogle() {
        viewModelScope.launch {
            try {
                // Generate a nonce (a random number used once)
                val hashedNonce = generateNonce()

                // Set up Google ID option
                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(appContext.getString(R.string.default_web_client_id))
                    .setNonce(hashedNonce)
                    .build()

                // Request credentials
                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()
                uiState.update { it.copy(signRequest = request) }
            } catch (e: Exception) {
                uiState.update { it.copy(isError = true) }
            }
        }
    }

    fun handleSignResult(response: GetCredentialResponse) {
        viewModelScope.launch {
            // Get the credential result
            val credential = response.credential

            // Check if the received credential is a valid Google ID Token
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)
                val authCredential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
               Firebase.auth.signInWithCredential(authCredential).await()
                uiState.update { it.copy(isUserSignedIn = true) }
            } else {
                uiState.update { it.copy(isError = true) }
            }
        }
    }


}
