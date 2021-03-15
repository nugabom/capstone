package com.example.myapplication.sikdangChoicePage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bookTime.BookTime

//프래그먼트내의 리사이클러뷰에 메뉴라인 바인드하는 어댑터 클래스
//SikdangChoiceMenuFragment 클래스의 bind 에서 사용
class SikdangChoiceMenuAdapter(var context : Context, var sikdangListReqData: SikdangListReqData):RecyclerView.Adapter<SikdangChoiceMenuAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.sikdangchoice_menuline, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }



    inner class Holder (itemView: View?) : RecyclerView.ViewHolder(itemView!!){

        fun bind(position:Int){
            //Log.d("확인 SikdangChoiceMenuAdapter", "Holder.bind")
            sikdangListReqData.setPos(position)
            var sikdangMenuData = SikdangMenuData(sikdangListReqData)
            var sikdangId = sikdangMenuData.getSikdangId()

            //Log.d("position확인 SikdangChoice", position.toString())


            //식당 이미지 불러와서 set
            var sikdangImage :ImageView = itemView.findViewById(R.id.sikdangImage)
            sikdangImage.setBackgroundResource(sikdangMenuData.getSikdangImage())

            //식당 이름 불러와서 set
            var sikdangNameTV : TextView = itemView.findViewById(R.id.sikdangName)
            sikdangNameTV.setText(sikdangMenuData.getSikdangName())

            //각 대표메뉴에 문자열 set
            var repMenuTV1 : TextView = itemView.findViewById(R.id.menuNameTV)
            var repMenuTV2 : TextView = itemView.findViewById(R.id.menuExpTV)
            var repMenuTV3 : TextView = itemView.findViewById(R.id.repMenuTV3)
            var repMenuTV4 : TextView = itemView.findViewById(R.id.menuPriceTV)

            repMenuTV1.setText(sikdangMenuData.getrepMenuArrayList()[0])
            repMenuTV2.setText(sikdangMenuData.getrepMenuArrayList()[1])
            repMenuTV3.setText(sikdangMenuData.getrepMenuArrayList()[2])
            repMenuTV4.setText(sikdangMenuData.getrepMenuArrayList()[3])

            var sikdangChoice_menuLine_dist:TextView = itemView.findViewById(R.id.sikdangChoice_menuLine_dist)
            sikdangChoice_menuLine_dist.setText(sikdangMenuData.getSikdangDist().toString()+"m")

            //순서대로 주차여부 쿠폰 미정
            //주차여부 0이면 주차 불가능 1이면 무료주차 2이면 유료주차
            //쿠폰 0이면 없음 1이면 있음
            var sikdangChoice_menuLine_park : TextView= itemView.findViewById(R.id.sikdangChoice_menuLine_park)
            if (sikdangMenuData.getanyList()[0] == 0){
                sikdangChoice_menuLine_park.setText("주차 불가")
            }else if (sikdangMenuData.getanyList()[0] == 1){
                sikdangChoice_menuLine_park.setText("무료 주차")
            }else if (sikdangMenuData.getanyList()[0] == 2){
                sikdangChoice_menuLine_park.setText("유료 주차")
            }

            var sikdangChoice_menuLine_coupon : TextView= itemView.findViewById(R.id.sikdangChoice_menuLine_coupon)
            if (sikdangMenuData.getanyList()[0] == 0){
                sikdangChoice_menuLine_coupon.setText("쿠폰 없음")
            }else if (sikdangMenuData.getanyList()[0] == 1){
                sikdangChoice_menuLine_coupon.setText("쿠폰 있음")
            }

            var menuLL : LinearLayout = itemView.findViewById(R.id.sikdangChoice_menuLine)
            menuLL.setOnClickListener(){
                val intent= Intent(itemView.context, BookTime::class.java)
                intent.putExtra("sikdangId", sikdangId)
                Log.d("확인 SikdangChoiceMenuAdapter ", "BookTime 호출")
                context.startActivity(intent)

            }


        }


    }
}