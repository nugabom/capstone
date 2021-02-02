package com.dsna19.test2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class total_login : AppCompatActivity() {
    lateinit var btn_customer : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_login)
        btn_customer = findViewById(R.id.btn_customer_login)
        btn_customer.setOnClickListener {
            val log_intent = Intent(this, customer_login::class.java)
            startActivity(log_intent)
        }
    }
}