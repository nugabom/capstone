package com.example.myapplication.bookTime

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class BookDataBuilder(val sikdangId : String) {
    var timeArrayList : ArrayList<String>? = null
    var isFullArrayList : ArrayList<Boolean> = arrayListOf()

    fun build() {
        var reference = FirebaseDatabase.getInstance().getReference("Tables")
                .child(sikdangId)
        Log.d("setData", "동기화 시작")

        val bookTree = hashMapOf<String, Boolean>()

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(store_table: DataSnapshot) {
                store_table.child("Booked")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

    data class IsFull(val current : Int? = 0, val max : Int? = 0)
}