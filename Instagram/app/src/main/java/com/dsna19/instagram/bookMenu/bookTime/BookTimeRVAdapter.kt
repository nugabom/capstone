package com.example.myapplication.bookTime

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//아예 그날 영업이 끝난 경우도 따로 상정해야할것

class BookTimeRVAdapter(var context: Context?, val bookData: BookData/*val bookTimeActivity:BookTime*/):RecyclerView.Adapter<BookTimeRVAdapter.Holder>() {
    var timeNumMax = bookData.timeArrayList.size
    var timePoint = timeset()

    private fun timeset():String {
        val now = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
        var current = now.format(timeFormatter)
        Log.d("timeset", current)

        return current
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context!!).inflate(R.layout.booktime_timeline, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return timeNumMax
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.time.text = bookData.timeArrayList[position]
        if(timePoint.compareTo(holder.time.text.toString(), false) > 0) {
            notifyInvalid(holder)
        }
        else if(bookData.isFull[bookData.timeArrayList.indexOf(holder.time.text.toString())]) {
            notifyInvalid(holder)
        }
        else {
            holder.time.setOnClickListener{
                showMenu(holder)
            }
        }
    }


    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        lateinit var time: TextView

        init {
            time = itemView.findViewById(R.id.time)
        }
    }

    fun notifyInvalid(holder : Holder) {
        holder.time.setBackgroundColor(Color.RED)
        holder.time.setOnClickListener {
            Toast.makeText(context!!, "예약하려고 하신 시간은 불가합니다..", Toast.LENGTH_SHORT).show()
        }

    }

    fun showMenu(holder : Holder) {
        Toast.makeText(context!!, "예약 성공", Toast.LENGTH_SHORT).show()
        //TableBuilder(bookData.sikdangId, "닭고기").build()
        TableBuilder(bookData.sikdangId, holder.time.text.toString()).build()
    }

    fun getFromDB(){

    }
}