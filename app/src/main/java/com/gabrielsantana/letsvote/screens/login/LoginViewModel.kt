package com.gabrielsantana.letsvote.screens.login

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabrielsantana.letsvote.MyApp
import com.gabrielsantana.letsvote.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    val uiState: StateFlow<LoginUiState>
        field = MutableStateFlow(LoginUiState())

    private val credentialManager by lazy {
        CredentialManager.create(appContext)
    }

    fun signOut() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            uiState.update { it.copy(isUserSignedIn = false) }
        }
    }

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
            } catch (e: GetCredentialCancellationException) {
                uiState.update { it.copy(isError = true) }
            } catch (e: Exception) {
                e.printStackTrace()
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
                val authResult = Firebase.auth.signInWithCredential(authCredential).await()
                uiState.update { it.copy(isUserSignedIn = true) }
            } else {
                uiState.update { it.copy(isError = true) }
            }
        }
    }


}

data class LoginUiState(
    val signRequest: GetCredentialRequest? = null,
    val isUserSignedIn: Boolean = false,
    val isError: Boolean =  false
)

fun generateNonce(): String {
    val ranNonce: String = UUID.randomUUID().toString()
    val bytes: ByteArray = ranNonce.toByteArray()
    val md: MessageDigest = MessageDigest.getInstance("SHA-256")
    val digest: ByteArray = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}