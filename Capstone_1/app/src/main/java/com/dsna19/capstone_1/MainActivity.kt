package com.dsna19.capstone_1

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var Naver_pay : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Naver_pay = findViewById(R.id.btn_naver_pay)

        Naver_pay.setOnClickListener {
            Toast.makeText(this@MainActivity, "결제중입니다.", Toast.LENGTH_SHORT).show()
            
        }
    }
}