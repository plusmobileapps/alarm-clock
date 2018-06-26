package com.plusmobileapps.clock

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

const val RC_SIGN_IN = 123

class FirebaseAuthHelper {

    private val providers = listOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
    )

    fun startAuth(fragment: Fragment) {
        fragment.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
    }

    fun startAuth(activity: AppCompatActivity) {
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
    }

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                //successful sign in
                val user = FirebaseAuth.getInstance().currentUser
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
            }
        }
    }

    fun signOut(context: Context) {
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener {

                }
    }

    fun deleteAccount(context: Context) {
        AuthUI.getInstance()
                .delete(context)
                .addOnCompleteListener {

                }
    }

}