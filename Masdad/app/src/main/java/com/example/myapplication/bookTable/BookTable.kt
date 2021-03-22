package com.example.myapplication.bookTable

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

//BookTimeRVAdapter에서 불러와진다
//식당 id와 시간대 받아와야 한다
class BookTable: AppCompatActivity() {

    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("확인 BookTable()", "onCreaTE")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booktable)
        val sikdangId = intent.getExtras()?.getInt("sikdangId")
        val bookTime : String = intent.getExtras()?.getString("tableTime")!!
        var bookTableData = BookTableData(sikdangId!!, bookTime)




        val fragment = TableFragment()
        //fragmentTransaction.add(R.id.bookFragment, fragment)
        //fragmentTransaction.commit()
    }
}