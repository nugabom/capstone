package com.dsna19.instagram

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import kotlin.system.measureTimeMillis

class RegisterActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var fullname : EditText
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var register : Button
    lateinit var txt_login : TextView

    lateinit var auth : FirebaseAuth
    lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        INIT_UI()
        INIT_DB()
    }

    private fun INIT_UI() {
        username = findViewById(R.id.username)
        fullname = findViewById(R.id.fullname)
        email    = findViewById(R.id.email)
        password = findViewById(R.id.password)
        register = findViewById(R.id.register)
        txt_login = findViewById(R.id.txt_login)

        txt_login.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        register.setOnClickListener {
            val str_username = username.text.toString()
            val str_fullname = fullname.text.toString()
            val str_email = email.text.toString()
            val str_password = password.text.toString()

            if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname)
                ||TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                Toast.makeText(this@RegisterActivity, "All fileds are required!", Toast.LENGTH_SHORT).show()
            } else if (str_password.length < 6) {
                Toast.makeText(this@RegisterActivity, "Password must have 6 characters!", Toast.LENGTH_SHORT).show()
            } else {
                register(str_username, str_fullname, str_email, str_password)
            }
        }
    }

    private fun INIT_DB() {
        auth = FirebaseAuth.getInstance()
    }
    private fun register(username: String, fullname: String, email: String, password: String) {
       auth.createUserWithEmailAndPassword(email, password)
           .addOnCompleteListener { task ->
               if (task.isSuccessful()) {
                   val firebaseUser = auth.currentUser
                   val userid = firebaseUser!!.uid

                   reference = FirebaseDatabase.getInstance().getReference()
                       .child("Users")
                       .child(userid)

                   var userInfo = hashMapOf<String, Any> (
                       "id" to userid,
                       "username" to username.toLowerCase(),
                       "fullname" to fullname,
                       "bio" to "",
                       "imageurl" to "https://firebasestorage.googleapis.com/v0/b/instagram-4e4f7.appspot.com/o/placeholder.png?alt=media&token=6716cb07-fdcf-42af-bf5f-dddc651161a7"
                   )

                   reference.setValue(userInfo).addOnCompleteListener { task
                       if (task.isSuccessful()) {
                           var intent = Intent(this@RegisterActivity, MainActivity::class.java)
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                           startActivity(intent)
                       }
                   }
               } else {
                   Toast.makeText(this, "You can't register with this email or pass", Toast.LENGTH_SHORT).show()
               }
           }
    }
}