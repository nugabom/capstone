package com.example.myapplication.bookActivity

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.floor

class TimeLineAdapter (var context: Context?,
                       val bookTime: BookTime,
                       val sikdangId : String,
                       val activity: BookActivity
) :RecyclerView.Adapter<TimeLineAdapter.ViewHolder>() {
    private val current_time = timeset()
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        lateinit var time : TextView
        init {
            time = itemView.findViewById(R.id.time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context!!).inflate(R.layout.booktime_timeline, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = bookTime.time_list[position]
        if("5:00 오후".compareTo(holder.time.text.toString(), false) > 0) {
            notifyInvalid(holder)
        }
        else if(bookTime.book_check_list[bookTime.time_list.indexOf(holder.time.text.toString())]) {
            notifyInvalid(holder)
        }
        else {
            holder.time.setOnClickListener{
                showMenu(holder)
            }
        }
    }

    override fun getItemCount(): Int {
        return bookTime.time_list.size
    }

    private fun timeset():String {
        val now = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
        var current = now.format(timeFormatter)
        Log.d("timeset", current)

        return current
    }

    fun notifyInvalid(holder : ViewHolder) {
        holder.time.setBackgroundColor(Color.RED)
        holder.time.setOnClickListener {
            Toast.makeText(context!!, "예약하려고 하신 시간은 불가합니다..", Toast.LENGTH_SHORT).show()
        }

    }

    fun showMenu(holder : ViewHolder) {
        Toast.makeText(context!!, "예약 성공", Toast.LENGTH_SHORT).show()
        val selected_time = holder.time.text.toString()
        FirebaseDatabase.getInstance().getReference("Tables")
            .child(sikdangId)
            .child("Booked").addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("showMenu", "${sikdangId}")
                    Log.d("showMenu", "${selected_time}")
                    Log.d("showMenu", snapshot.key.toString())
                    val done = getTable(snapshot, selected_time)
                    if(done == null) return
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun getTable(snapshot: DataSnapshot, time : String) :Boolean{
        var floor_map= hashMapOf<String, HashMap<String, Boolean>>()
        Log.d("getTable", "${snapshot}")
        for (floor in snapshot.children) {
            Log.d("getTable", "시바바바${floor.toString()}")
            Log.d("getTable", floor.child(time).key.toString())

            val floor_name = floor.key.toString()
            val tables = hashMapOf<String, Boolean>()

            val tableCnt = floor.child(time).childrenCount - 2
            Log.d("getTable", tableCnt.toString())
            for(i in 0 until tableCnt.toInt()) {
                val data = floor.child(time).child("table${i + 1}").getValue(IsTableBooKed::class.java)
                if(data == null) {return false}
                val alreadyBooked = data!!.mutex
                if(alreadyBooked == 1) {
                    tables.put("table${i + 1}", false)
                } else {
                    tables.put("table${i + 1}", true)
                }
            }
            if(!tables.isEmpty()) {
                floor_map.put(floor_name, tables)
            }
        }
        for (floor_name in floor_map.keys) {
            Log.d("getTable", "${floor_name} : ${floor_map[floor_name]}")
        }

        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.bookFragment, TableFragment(activity.tableMetaData, floor_map, time))
                .commit()
        return true
    }
}

data class IsTableBooKed(val mutex : Int?= null /* 명수 */)