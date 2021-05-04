package com.example.myapplication.bookActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.mainPage.MainActivity
import com.example.myapplication.start.StartActivity

class PayComplete : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_complete)
    }

    override fun onBackPressed() {
        val _intent = Intent(this, MainActivity::class.java)
        _intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(_intent)
        finish()
    }
}