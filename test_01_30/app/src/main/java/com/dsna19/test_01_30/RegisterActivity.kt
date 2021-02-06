package com.dsna19.test_01_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var username : EditText
    lateinit var phone_number : EditText
    lateinit var email : EditText
    lateinit var password : EditText

    lateinit var register : Button
    lateinit var txt_login : TextView

    lateinit var auth : FirebaseAuth
    lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        username = findViewById(R.id.username)
        phone_number = findViewById(R.id.phone_number)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        register = findViewById(R.id.register)
        txt_login = findViewById(R.id.txt_login)

        register.setOnClickListener {
            val str_username = username.text.toString()
            val str_phone_number = phone_number.text.toString()
            val str_email = email.text.toString()
            val str_password = password.text.toString()

            if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_phone_number)
                || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                Toast.makeText(this@RegisterActivity, "All fileds are required!", Toast.LENGTH_SHORT).show()
            } else if (str_password.length < 6) {
                Toast.makeText(this@RegisterActivity, "Password must have 6 characters!", Toast.LENGTH_SHORT).show()
            } else {
                register(str_username, str_phone_number, str_email, str_password)
            }
        }

    }

    private fun register (username : String, phone_number : String,
                          email : String, password : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(!task.isSuccessful) {
                    Toast.makeText(this, "가입 실패", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener;
                }

                val firebaseUser = auth.currentUser
                val userid = firebaseUser!!.uid
                reference = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(userid)

                val userInfo = hashMapOf<String, Any>(
                    "id" to userid,
                    "username" to username.toLowerCase(),
                    "phone_number" to phone_number,
                    "email" to firebaseUser.email.toString()
                )

                reference.setValue(userInfo).addOnCompleteListener {
                    if(!task.isSuccessful) {
                        Log.d("register", userid + " : Database 유저정보 업데이트 실패")
                        return@addOnCompleteListener ;
                    }

                    var intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
    }
}