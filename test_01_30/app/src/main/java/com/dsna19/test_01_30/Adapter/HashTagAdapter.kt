package com.dsna19.test_01_30.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.dsna19.test_01_30.ConnectHashTag
import com.dsna19.test_01_30.DataClass.HashTag
import com.dsna19.test_01_30.Fragment.HomeFragment
import com.dsna19.test_01_30.Fragment.MarketFragment
import com.dsna19.test_01_30.R

class HashTagAdapter(var mContext : Context?,var mHashTagList : ArrayList<HashTag>)
    :RecyclerView.Adapter<HashTagAdapter.ViewHolder>()
{
        inner class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
            var tag : TextView = itemview.findViewById(R.id.tag)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.hash_tag_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hashTag = mHashTagList[position]

        holder.tag.text = "#" + hashTag.tag
        holder.tag.tag = hashTag.tag

        holder.itemView.setOnClickListener {
            var intent = Intent(mContext, ConnectHashTag::class.java)
            intent.putExtra("hashtag_uri", holder.tag.tag as String)
            mContext!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mHashTagList.size
    }
}