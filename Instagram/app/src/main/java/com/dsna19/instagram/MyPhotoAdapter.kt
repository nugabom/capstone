package com.dsna19.instagram

import android.content.Context
import android.gesture.GestureLibraries
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyPhotoAdapter (var mContext : Context?, var mPhotos : ArrayList<Post>)
    :RecyclerView.Adapter<MyPhotoAdapter.ViewHolder>(){

    inner class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var post_image : ImageView

        init {
            post_image = itemview.findViewById(R.id.post_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext!!).inflate(R.layout.my_photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = mPhotos[position]

        Glide.with(mContext!!).load(post.postimage).into(holder.post_image)

        holder.post_image.setOnClickListener {
            var editor = mContext!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            editor.putString("postid", post.postid)
            editor.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostDetailFragment())
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }
}