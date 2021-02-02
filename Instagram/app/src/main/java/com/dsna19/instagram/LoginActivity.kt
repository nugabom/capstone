package com.dsna19.instagram

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.net.PasswordAuthentication

class LoginActivity : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var login : Button
    lateinit var txt_signup : TextView

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        INIT_UI()
    }

    private fun INIT_UI() {
        email = findViewById(R.id.email)
        login = findViewById(R.id.login)
        password = findViewById(R.id.password)
        txt_signup = findViewById(R.id.txt_signup)

        auth = FirebaseAuth.getInstance()

        txt_signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        login.setOnClickListener {
            val str_email = email.text.toString()
            val str_password = password.text.toString()

            if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                Toast.makeText(this@LoginActivity, "All fileds are required!", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(str_email, str_password).addOnCompleteListener { task ->
                    if(task.isSuccessful()) {
                        var reference = FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.currentUser!!.uid)

                        val loginEventListener = object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                finish()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        }
                        reference.addValueEventListener(loginEventListener)

                    } else {
                        Toast.makeText(this, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}