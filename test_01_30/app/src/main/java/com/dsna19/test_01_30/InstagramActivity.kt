package com.dsna19.test_01_30

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.util.*


class InstagramActivity : AppCompatActivity() {
    lateinit var loginButton : LoginButton
    lateinit var callbackManager : CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instagram)

        
    }


/*
        loginButton = findViewById<View>(R.id.login_button) as LoginButton
        loginButton.setReadPermissions("email")
        // If using in a fragment
        // If using in a fragment

        // Callback registration
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })


        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })

        var accessToken = AccessToken.getCurrentAccessToken()
        var isLoggedIn = accessToken != null && !accessToken!!.isExpired

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"))
        Log.d("facebook id",accessToken.permissions.toString())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }
 */
}