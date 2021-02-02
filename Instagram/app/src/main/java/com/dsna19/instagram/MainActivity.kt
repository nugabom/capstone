package com.dsna19.instagram

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigator: BottomNavigationView
    private var selectedFragment : Fragment? = null
    private var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    selectedFragment = HomeFragment()
                }

                R.id.nav_search -> {
                    selectedFragment = SearchFragment()
                }

                R.id.nav_add -> {
                    selectedFragment = null
                    startActivity(Intent(this@MainActivity, PostActivity::class.java))
                }

                R.id.nav_heart -> {
                    selectedFragment = NotificationFragment()
                }

                R.id.nav_profile -> {
                    var editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit()
                    editor.putString("profileid", FirebaseAuth.getInstance().currentUser!!.uid)
                    editor.apply()
                    selectedFragment = ProfileFragment()
                }
            }

            if(selectedFragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    selectedFragment!!
                ).commit()
            }

            return@OnNavigationItemSelectedListener true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigator = findViewById(R.id.bottom_navigation)
        bottomNavigator.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

        val intent = intent.extras
        if (intent != null) {
            val publisher = intent.getString("publisherid")
            val editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit()
            editor.putString("profileid", publisher)
            editor.apply()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment()).commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
        }
    }
    

}