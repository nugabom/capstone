package com.example.myapplication.bookTable

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
//BookPersonDialog에서 사용
//다이얼로그의 인원수 나타내는 버튼을 리사이클러뷰에 바인드
class PersonNumRVAdapter(var context: Context, val maxP:Int, val bookPersonDialog: BookPersonDialog, val floor:Int, var curP:Int):RecyclerView.Adapter<PersonNumRVAdapter.Holder>() {
    var numButtonAR = ArrayList<Button>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.generalitem_button, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return maxP
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }


    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        public fun bind(pos:Int){
            var count = pos
            var button = itemView.findViewById<Button>(R.id.gItem_button)
            //button.width = 5
            button.setText((pos+1).toString())
            if((curP != 0) and (curP == (pos+1))){
                button.setBackgroundColor(Color.parseColor("#55CC55"))
            }

            var pNum = pos+1
            button.setOnClickListener {
                //버튼 클릭하면 이전 액티비티로 데이터 전달
                button.setBackgroundColor(Color.parseColor("#55CC55"))
                var i = 0
                while(i<numButtonAR.size){
                    if (i!=count){
                        //Log.d("확인 버튼 설정", i.toString()+ pos.toString()+count.toString())
                        numButtonAR[i].setBackgroundColor(Color.parseColor("#FFFFFF"))
                    }
                    i++
                }
                bookPersonDialog.buttonClicked(pNum)
            }
            numButtonAR.add(button)

        }


    }
}