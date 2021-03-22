package com.example.myapplication.payPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CouponListRVAdapter(var context:Context, val couponData: CouponData, val payPageCouponDialog: PayPageCouponDialog): RecyclerView.Adapter<CouponListRVAdapter.Holder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.paypage_coupondialog_couponline, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return payPageCouponDialog.couponMatchNumList.size
        //return 6
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){
        public fun bind(pos:Int){
            var couponExplanationTV:TextView = itemView.findViewById(R.id.couponExplanationTV)
            couponExplanationTV.setText(couponData.couponList[payPageCouponDialog.couponMatchNumList[pos]].explanation)
            var minPriceTV:TextView = itemView.findViewById(R.id.minPriceTV)
            minPriceTV.setText("최소 "+couponData.couponList[payPageCouponDialog.couponMatchNumList[pos]].minPrice.toString()+"원 이상 사용 가능")
            var overlapTV:TextView = itemView.findViewById(R.id.overlapTV)
            if(couponData.couponList[payPageCouponDialog.couponMatchNumList[pos]].overlap == 1){
                overlapTV.setText("중복 가능")
            }
            else if(couponData.couponList[payPageCouponDialog.couponMatchNumList[pos]].overlap == 2){
                overlapTV.setText("중복 불가")
            }
            else if(couponData.couponList[payPageCouponDialog.couponMatchNumList[pos]].overlap == 3){
                overlapTV.setText("추가중복 가능")
            }

            var couponEXPTV:TextView = itemView.findViewById(R.id.couponEXPTV)
            couponEXPTV.setText(couponData.couponList[payPageCouponDialog.couponMatchNumList[pos]].couponExp.toString()+"까지 사용 가능")


        }
    }
}