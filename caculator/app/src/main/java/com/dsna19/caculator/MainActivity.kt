package com.dsna19.caculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    lateinit var btn_add : Button
    lateinit var btn_sub : Button
    lateinit var btn_mul : Button
    lateinit var btn_div : Button
    lateinit var btn_mod : Button

    lateinit var edit_lhs : EditText
    lateinit var edit_rhs : EditText
    lateinit var edit_result: TextView

    lateinit var lhs : String
    lateinit var rhs : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "간단 계산기"
        setTitle(title)

        btn_add = findViewById(R.id.btn_plus)
        btn_sub = findViewById(R.id.btn_minus)
        btn_mul = findViewById(R.id.btn_mult)
        btn_div = findViewById(R.id.btn_div)
        btn_mod = findViewById(R.id.btn_mod)

        edit_lhs = findViewById(R.id.text_lhs)
        edit_rhs = findViewById(R.id.text_rhs)
        edit_result = findViewById(R.id.text_result)

        btn_add.setOnTouchListener {view, motionEvent->
            lhs = edit_lhs.text.toString()
            rhs = edit_rhs.text.toString()
            var result = Integer.parseInt(lhs) + Integer.parseInt(rhs)
            edit_result.setText("계산 결과 : $result")
            println(result)
            false
        }
        btn_sub.setOnTouchListener { v, event ->
            lhs = edit_lhs.text.toString()
            rhs = edit_rhs.text.toString()
            var result = Integer.parseInt(lhs) - Integer.parseInt(rhs)
            edit_result.setText("계산 결과 : $result")
            println(result)
            false
        }
        btn_mul.setOnTouchListener { v, event ->
            lhs = edit_lhs.text.toString()
            rhs = edit_rhs.text.toString()
            var result = Integer.parseInt(lhs) * Integer.parseInt(rhs)
            edit_result.setText("계산 결과 : $result")
            println(result)
            false
        }
        btn_div.setOnTouchListener { v, event ->
            lhs = edit_lhs.text.toString()
            rhs = edit_rhs.text.toString()
            if (Integer.parseInt(rhs) == 0) {
                Toast.makeText(applicationContext, "분모가 0입니다!", Toast.LENGTH_SHORT).show()
                false
            }
            var result = Integer.parseInt(lhs) / Integer.parseInt(rhs)
            edit_result.setText("계산 결과 : $result")
            println(result)
            false
        }
        btn_mod.setOnTouchListener { v, event ->
            lhs = edit_lhs.text.toString()
            rhs = edit_rhs.text.toString()
            if (Integer.parseInt(rhs) == 0) {
                Toast.makeText(applicationContext, "분모가 0입니다!", Toast.LENGTH_SHORT).show()
                false
            }
            var result = Integer.parseInt(lhs) % Integer.parseInt(rhs)
            edit_result.setText("계산 결과 : $result")
            println(result)
            false
        }
    }
}