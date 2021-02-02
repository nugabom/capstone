package com.dsna19.test_01_30.Adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dsna19.test_01_30.DataClass.MenuType
import com.dsna19.test_01_30.R

class MenuTypeAdapter (var mContext : Context?, var  mMenuTypeList : ArrayList<MenuType>)
    : RecyclerView.Adapter<MenuTypeAdapter.ViewHolder>() {
    inner class ViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {
        var menu_image : ImageView = itemview.findViewById(R.id.menu_image)
        var menu_label : TextView = itemview.findViewById(R.id.menu_label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.menu_type_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuType = mMenuTypeList[position]

        holder.menu_image.setImageResource(menuType.imageUri)
        holder.menu_label.text = menuType.label

        holder.itemView.setOnClickListener {
            Toast.makeText(mContext,
                holder.menu_label.text.toString() + " : 눌려졌다.",
                Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return mMenuTypeList.size
    }
}