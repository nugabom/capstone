package com.dsna19.test2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.set

class customer_login : AppCompatActivity() {
    lateinit var btn_login : Button
    lateinit var id_text : EditText
    lateinit var pw_text : EditText
    val ID_ADMIN = "ADMIN"
    val PW_ADMIN = "0000"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)
        btn_login = findViewById(R.id.btn_login_button)
        id_text = findViewById(R.id.text_customer_id)
        pw_text = findViewById(R.id.text_customer_pw)
        btn_login.setOnClickListener{
            if (this.login()) {
                Toast.makeText(
                    this.applicationContext,
                    R.string.success_login_toast,
                    Toast.LENGTH_LONG
                ).show()
                Log.d("btn_customer_login","로그인 성공")
                var go_home = Intent(this, MainActivity::class.java)
                go_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(go_home)
                finish()
            }else {
                Toast.makeText(this, R.string.fail_login_toast, Toast.LENGTH_SHORT).show()
                Log.d("btn_customer_login","로그인 실패")
            }
        }
    }
    private fun login(): Boolean {
        val id = id_text.text.toString()
        val pw = pw_text.text.toString()
        id_text.setText("")
        pw_text.setText("")
        Log.d("id", id)
        Log.d("pw", pw)
        return (id.equals(ID_ADMIN)) && (pw.equals(PW_ADMIN))
    }
}