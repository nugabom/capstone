package com.dsna19.test_01_30.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dsna19.test_01_30.DataClass.HashTag
import com.dsna19.test_01_30.R
import java.util.zip.Inflater

class HashTagAdapter(var mContext : Context?, var mHashTagList : ArrayList<HashTag>)
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

        holder.itemView.setOnClickListener {
            Toast.makeText(mContext,
                            holder.tag.text.toString() + ": 눌렸습니다.",
                            Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mHashTagList.size
    }
}