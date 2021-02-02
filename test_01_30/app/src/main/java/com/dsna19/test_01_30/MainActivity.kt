package com.dsna19.test_01_30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dsna19.test_01_30.Fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_cotainer, HomeFragment())
            .commit()
    }
}