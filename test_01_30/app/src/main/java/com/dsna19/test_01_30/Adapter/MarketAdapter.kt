package com.dsna19.test_01_30.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dsna19.test_01_30.DataClass.HashTag
import com.dsna19.test_01_30.DataClass.Market
import com.dsna19.test_01_30.R

class MarketAdapter(var mContext : Context?, var mMarketList : ArrayList<Market>)
    : RecyclerView.Adapter<MarketAdapter.ViewHolder>()
{
    inner class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.market_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val market = mMarketList[position]
    }

    override fun getItemCount(): Int {
        return mMarketList.size
    }
}