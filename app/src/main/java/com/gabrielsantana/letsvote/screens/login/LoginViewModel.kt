package com.gabrielsantana.letsvote.screens.login

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.lifecycle.ViewModel
import com.gabrielsantana.letsvote.MyApp
import com.gabrielsantana.letsvote.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.common.GoogleSignatureVerifier
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

class LoginViewModel : ViewModel() {

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        if (result.credential is GoogleIdTokenCredential) {
            
        }
    }


}