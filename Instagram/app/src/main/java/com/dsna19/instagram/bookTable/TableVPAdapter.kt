package com.example.myapplication.bookTable

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.bookTime.BookData

//TableFragment에서 사용 TableFloorFragmenr에 ㅐbundle로 BookData와 position 집어넣음
class TableVPAdapter(fa:FragmentActivity, val bookData: BookData, val tableData: TableData): FragmentStateAdapter(fa) {
    //총 몇층인가

    override fun getItemCount(): Int {
        //Log.d("확인 TableVPAdapter", "getItemCount()")
        return bookData.getFloor()
    }

    override fun createFragment(position: Int): Fragment {
        //Log.d("확인 TableVPAdapter", "createFragment(position: Int)")
        var tableFloorFragment=TableFloorFragment()
        //Log.d("확인 TableVPAdapter", "createFragment(position: Int)2")
        bind(position, tableFloorFragment)
        return tableFloorFragment
    }

    private fun bind(pos:Int, fragment:Fragment){
        Log.d("확인 TableVPAdapter", "bind")
        //프래그먼트에 번들로 데이터 집어넣음
        var bundle: Bundle = Bundle()
        bundle.putSerializable("bookData", bookData)
        //Log.d("확인 TableVPAdapter", "bind2")
        bundle.putSerializable("tableData", tableData)
        bundle.putInt("pos", pos)
        fragment.setArguments(bundle)
        //Log.d("확인 TableVPAdapter", "bind3")

    }



}