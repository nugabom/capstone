package com.dsna19.test_01_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class StartActivity : AppCompatActivity() {
    var firebaseUser: FirebaseUser? = null
    lateinit var manufacturer_login : Button
    lateinit var user_login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        manufacturer_login = findViewById(R.id.manufacturer_login)
        user_login = findViewById(R.id.user_login)

        user_login.setOnClickListener {
            startActivity(Intent(this@StartActivity, CustomerLogInActivity::class.java))
            finish()
        }

        manufacturer_login.setOnClickListener {
            Toast.makeText(this, "미구현", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()

        firebaseUser = FirebaseAuth.getInstance().currentUser

        // 여기서 firebaseUser와
        // firebaseDatabase로 사용자와 판매자 구별해서 UI변경
        // FirebaseDatabase.getInstance().getReference(/* 사용자 */)
        // reference.childExists(firebaseUser) -> Main

        if(firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}