package com.example.myapplication.bookTime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

//BookTime 의 프래그먼트에 붙일 리사이클러뷰를 설정

class TimeFragment(val bookData: BookData) : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var bookTimeRVAdapter : BookTimeRVAdapter
    //북 타임 데이터는 BookTime에서 데이터 받아와야함 원래

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.booktime_fragment, container, false)
        bind(view)
        return view
    }


    public fun bind(itemView: View){
        /*
        var bundle = getArguments()
        if (bundle != null) bookData = bundle.getSerializable("bookData") as BookData
        else{
            Log.d("확인 TimeFragment.bind", "else")
        }
        var bookTimeActivity= activity as BookTime

        */
        //bookData = bundle!!.getSerializable("bookData") as BookData
        //Log.d("확인 TimeFragment.bind", bookData.getSikdangName())
        bookTimeRVAdapter = BookTimeRVAdapter(context, bookData)
        recyclerView = itemView.findViewById(R.id.bookTimeRV2)
        Log.d("timeFragment", "${bookData.timeArrayList}")
        Log.d("timeFragment", "${bookData}")
        recyclerView.setHasFixedSize(true)
        val GridLayoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = GridLayoutManager
        recyclerView.adapter = bookTimeRVAdapter
        //var bookTimeRVAdapter = BookTimeRVAdapter(this!!.getActivity()!!, bookData, bookTimeActivity)
        /*
        bookTimeRV.adapter = bookTimeRVAdapter

        var sikdangChice_catLineLM = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        bookTimeRV.layoutManager=sikdangChice_catLineLM
        bookTimeRV.setHasFixedSize(true)
        */
    }
}
