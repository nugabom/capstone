package com.example.myapplication.bookActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.ActivityChooserView
import com.example.myapplication.R
import com.example.myapplication.dataclass.StoreInfo
import java.sql.Time

class BookActivity : AppCompatActivity() {
    lateinit var bookTime: BookTime
    lateinit var storeInfo: StoreInfo
    lateinit var sikdangName : TextView
    lateinit var sikdangImage : ImageView
    lateinit var tableMetaData : HashMap<String, HashMap<String, Table>>
    lateinit var menuList : ArrayList<MenuData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        storeInfo = intent.getSerializableExtra("storeInfo") as StoreInfo
        bookTime = intent.getSerializableExtra("bookTime") as BookTime
        tableMetaData = intent.getSerializableExtra("TableMetaData") as HashMap<String, HashMap<String, Table>>
        menuList = intent.getSerializableExtra("menuList") as ArrayList<MenuData>
        sikdangImage = findViewById(R.id.sikdangImage)
        sikdangName = findViewById(R.id.sikdangName)

        sikdangName.text = storeInfo.store_name

        supportFragmentManager.beginTransaction().replace(R.id.bookFragment, WaitFragment()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.bookFragment, TimeBookFragment(bookTime)).commit()
    }
}