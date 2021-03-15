package com.example.myapplication.start

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.mainPage.Sikdang_main
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

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

        password.setOnClickListener{finish()}
        password.imeOptions = EditorInfo.IME_ACTION_DONE
        password.setOnEditorActionListener ( object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    val str_username = username.text.toString()
                    val str_phone_number = phone_number.text.toString()
                    val str_email = email.text.toString()
                    val str_password = password.text.toString()


                    if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_phone_number)
                        || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                        Toast.makeText(this@RegisterActivity, "빈칸을 채워주세요!!", Toast.LENGTH_SHORT).show()
                    } else if (str_password.length < 6) {
                        Toast.makeText(this@RegisterActivity, "패스워드는 6글자 이상입니다!!", Toast.LENGTH_SHORT).show()
                    } else {
                        register(str_username, str_phone_number, str_email, str_password)
                    }
                    return true
                }
                return false;
            }
        })

        register.setOnClickListener {
            val str_username = username.text.toString()
            val str_phone_number = phone_number.text.toString()
            val str_email = email.text.toString()
            val str_password = password.text.toString()

            if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_phone_number)
                || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                Toast.makeText(this@RegisterActivity, "빈칸을 채워주세요!!", Toast.LENGTH_SHORT).show()
            } else if (str_password.length < 6) {
                Toast.makeText(this@RegisterActivity, "패스워드는 6글자 이상입니다!!", Toast.LENGTH_SHORT).show()
            } else {
                register(str_username, str_phone_number, str_email, str_password)
            }
        }
    }

    private fun register (username : String, phone_number : String,
                          email : String, password : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(this, "가입 실패", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener;
                } else {
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
                        if (task.isSuccessful) {
                            var intent = Intent(this, Sikdang_main::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this@RegisterActivity, "가입실패", Toast.LENGTH_SHORT).show()
                            firebaseUser.delete()
                        }
                    }
                }
            }
    }
}