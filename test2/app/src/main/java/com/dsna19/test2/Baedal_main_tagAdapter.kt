package com.dsna19.test2

import android.content.Context
import android.view.View
import android.widget.ToggleButton
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle
import android.widget.BaseAdapter
import android.R.attr.data
import android.content.ClipData

import androidx.appcompat.app.AppCompatActivity

import android.widget.Button
import android.widget.ExpandableListAdapter
import android.widget.LinearLayout
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView


class Baedal_main_tagAdapter(var context: Context, var tagList: List<TagLine>) : RecyclerView.Adapter<Baedal_main_tagAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.baedal_main_tagline, parent, false)
        return Holder(view)
    }


    override fun getItemCount(): Int {
        return tagList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(tagList[position], context)
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var toggleBtn1= itemView?.findViewById<ToggleButton>(R.id.toggleBtn_tagline1);
        var toggleBtn2= itemView?.findViewById<ToggleButton>(R.id.toggleBtn_tagline2);
        var toggleBtn3= itemView?.findViewById<ToggleButton>(R.id.toggleBtn_tagline3);
        fun bind(item: TagLine, context:Context) {

            toggleBtn1?.text=item.tag1;
            toggleBtn1?.textOn=item.tag1;
            toggleBtn1?.textOff=item.tag1;

            toggleBtn2?.text=item.tag2;
            toggleBtn2?.textOn=item.tag2;
            toggleBtn2?.textOff=item.tag2;

            toggleBtn3?.text=item.tag3;
            toggleBtn3?.textOn=item.tag3;
            toggleBtn3?.textOff=item.tag3;

        }
    }




}