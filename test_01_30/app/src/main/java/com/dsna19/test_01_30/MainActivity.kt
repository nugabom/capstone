package com.dsna19.test_01_30

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dsna19.test_01_30.Fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigator: BottomNavigationView
    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, StartActivity::class.java))
                    finish()
                }
            }

            return@OnNavigationItemSelectedListener true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigator = findViewById(R.id.bottom_navigation)
        bottomNavigator.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_cotainer, HomeFragment())
            .commit()
    }
}