package com.dsna19.myapplication

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ImageAdapter(var mContext : Context, var mUploads: ArrayList<Upload>, var mListener : OnItemClickListener)
    : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView : View)
        : RecyclerView.ViewHolder(itemView)
            ,View.OnClickListener
            ,View.OnCreateContextMenuListener
            ,MenuItem.OnMenuItemClickListener{
        var textViewName : TextView = itemView.findViewById(R.id.text_view_name)
        var imageView : ImageView = itemView.findViewById(R.id.image_view_upload)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnCreateContextMenuListener(this)
        }
        override fun onClick(v: View?) {
            if (mListener != null) {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position)
                }
            }
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu!!.setHeaderTitle("Select Action")
            val doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever")
            val delete = menu.add(Menu.NONE, 2, 2, "Delete")

            doWhatever.setOnMenuItemClickListener(this)
            delete.setOnMenuItemClickListener (this)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            if (mListener != null) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    when (item!!.itemId) {
                        1 -> {
                            mListener.onWhatEverClick(position)
                            return true
                        }
                        2-> {
                            mListener.onDeleteClick(position)
                            return true
                        }
                        else -> return false
                    }
                }
            }
            return false
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position : Int)

        fun onWhatEverClick(position: Int)

        fun onDeleteClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        var v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val upLoadCurrent = mUploads[position]
        Log.d("OnBindViewHodler", "I'm called")
        holder.textViewName.text = upLoadCurrent.getName()
        Picasso.with(mContext)
            .load(upLoadCurrent.getImageUri())
            .placeholder(R.mipmap.ic_launcher)
            .fit()
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return mUploads.size
    }


}