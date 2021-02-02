package com.dsna19.instagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class OptionsActivity : AppCompatActivity() {
    lateinit var logout : TextView
    lateinit var settings : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        logout = findViewById(R.id.logout)
        settings = findViewById(R.id.settings)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "Options"
        toolbar.visibility = View.VISIBLE
        toolbar.setNavigationOnClickListener {
            finish()
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, StartActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }
}