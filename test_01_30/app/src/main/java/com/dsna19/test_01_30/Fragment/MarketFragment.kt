package com.dsna19.test_01_30.Fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsna19.test_01_30.Adapter.MarketAdapter
import com.dsna19.test_01_30.Adapter.MenuTypeAdapter
import com.dsna19.test_01_30.DataClass.Market
import com.dsna19.test_01_30.DataClass.MenuType
import com.dsna19.test_01_30.R

class MarketFragment(val id: String) : Fragment() {
    lateinit var test : TextView

    lateinit var market_recycler_view: RecyclerView
    lateinit var market_Adapter: MarketAdapter
    lateinit var marktet_List : ArrayList<Market>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_marget, container, false)

        market_recycler_view = view.findViewById(R.id.market_recycler_view)
        market_recycler_view.setHasFixedSize(true)
        market_recycler_view.focusable = View.FOCUSABLE
        val market_layout = LinearLayoutManager(context)
        market_recycler_view.layoutManager = market_layout


        marktet_List = arrayListOf()
        for (i in 0 until 10) {
            marktet_List.add(Market())
        }
        market_Adapter = MarketAdapter(context, marktet_List)
        market_recycler_view.adapter = market_Adapter
        market_Adapter.notifyDataSetChanged()
        return view
    }
}