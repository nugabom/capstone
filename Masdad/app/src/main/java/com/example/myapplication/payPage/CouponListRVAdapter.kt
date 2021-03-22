package com.example.myapplication.payPage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.mainPage.SikdangMainCatAdapter

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
            var couponEXPTV:TextView = itemView.findViewById(R.id.couponEXPTV)
            couponEXPTV.setText(couponData.couponList[payPageCouponDialog.couponMatchNumList[pos]].explanation)

        }
    }
}