package com.dsna19.myapplication

import android.content.ClipData
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter(val context: Context, val mExampleList :ArrayList<item>) : RecyclerView.Adapter<ItemAdapter.ItemHolder> (){

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mImageView = itemView.findViewById<ImageView>(R.id.image_view)
        val mTextViewCreator = itemView.findViewById<TextView>(R.id.text_view_creator)
        val mTextViewLike = itemView.findViewById<TextView>(R.id.text_view_downloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var currentItem : item = mExampleList[position]

        val imageUrl = currentItem.mImageUrl
        val creatorName = currentItem.mCreator
        val likeCount = currentItem.mLikes

        holder.mTextViewCreator.text = creatorName
        holder.mTextViewLike.setText("Likes: "+ likeCount)
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView)
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }
}