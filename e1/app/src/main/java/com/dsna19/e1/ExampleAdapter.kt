package com.dsna19.e1

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.DraweeView
import com.facebook.drawee.view.SimpleDraweeView

class ExampleAdapter(exampleList : ArrayList<ExampleItem>, context: Context) :
    RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>(){

    private var mExampleList: ArrayList<ExampleItem>
    private var mContext : Context
    init {
        mExampleList  = exampleList
        Fresco.initialize(context)
        mContext = context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = mExampleList[position]


        var uri : Uri = Uri.parse(currentItem.imageResource)
        holder.imageView.setImageURI(uri, mContext)
        holder.textView1.text = currentItem.Title
        holder.textView2.text = currentItem.Content
    }

    override fun getItemCount() = mExampleList.size

    inner class ExampleViewHolder(itemview :View) : RecyclerView.ViewHolder (itemview) {
        val imageView: SimpleDraweeView = itemview.findViewById(R.id.ImageView_title)
        val textView1: TextView = itemView.findViewById(R.id.TextView_title)
        val textView2: TextView = itemView.findViewById(R.id.TextView_content)
    }
}