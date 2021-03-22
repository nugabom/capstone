package com.example.myapplication.bookTime

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TableBuilder(val sikdangId : String, val time : String) {
    fun build() {
        var reference = FirebaseDatabase.getInstance().getReference("Tables")
                .child(sikdangId)
                .child("Booked")

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var floorMap = hashMapOf<String, ArrayList<Boolean>>()
                for (floor in snapshot.children) {
                    var Floors = arrayListOf<Boolean>()
                    if(floor.child(time).exists()) {
                        val nTables = floor.child(time).childrenCount - 2
                        Log.d("nTables", nTables.toString())
                        for(i in 0 until nTables) {
                            var mutex = floor.child(time).child("table${i + 1}").getValue(_Table::class.java)!!.mutex!!.toInt()
                            if(mutex == 1) {
                                Floors.add(true)
                            } else {
                                Floors.add(false)
                            }
                            Log.d("Floors", "${Floors}")
                        }
                    }
                    floorMap.put(floor.key.toString(), Floors)
                    Log.d(floor.key, "${Floors}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}

data class _Table(val mutex : Long?=null)