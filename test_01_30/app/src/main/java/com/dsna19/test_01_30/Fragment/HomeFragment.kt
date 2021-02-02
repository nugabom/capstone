package com.dsna19.test_01_30.Fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dsna19.test_01_30.Adapter.BannerAdapter
import com.dsna19.test_01_30.Adapter.HashTagAdapter
import com.dsna19.test_01_30.Adapter.MenuTypeAdapter
import com.dsna19.test_01_30.DataClass.Banner
import com.dsna19.test_01_30.DataClass.HashTag
import com.dsna19.test_01_30.DataClass.MenuType
import com.dsna19.test_01_30.R
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    lateinit var banner_recycler_view: RecyclerView
    lateinit var bannerAdapter: BannerAdapter
    lateinit var bannerList: ArrayList<Banner>
    lateinit var banner_layout : LinearLayoutManager

    lateinit var menu_type_recycler_view: RecyclerView
    lateinit var menuTypeAdapter: MenuTypeAdapter
    lateinit var menuTypeList : ArrayList<MenuType>

    lateinit var hash_tag_menu_recycler_view: RecyclerView
    lateinit var hashTagAdapter: HashTagAdapter
    lateinit var hashTagList : ArrayList<HashTag>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        banner_recycler_view = view.findViewById(R.id.banner_recycler_view)
        banner_recycler_view.setHasFixedSize(true)
        banner_layout = LinearLayoutManager(context)
        banner_layout.orientation = LinearLayoutManager.HORIZONTAL
        banner_recycler_view.layoutManager = banner_layout
        bannerList = arrayListOf(
            Banner(R.drawable.ic_placeholder, "1"),
            Banner(R.drawable.ic_add, "2"),
            Banner(R.drawable.ic_book, "3")
        )
        bannerAdapter = BannerAdapter(context, bannerList)
        banner_recycler_view.adapter = bannerAdapter
        bannerAdapter.notifyDataSetChanged()



        menu_type_recycler_view = view.findViewById(R.id.menu_type_recycler_view)
        menu_type_recycler_view.setHasFixedSize(true)
        var menu_type_layout = GridLayoutManager(context, 2)
        menu_type_layout.orientation = LinearLayoutManager.HORIZONTAL
        menu_type_recycler_view.layoutManager = menu_type_layout
        menuTypeList = arrayListOf(
            MenuType(R.drawable.ic_placeholder, "1"),
            MenuType(R.drawable.ic_placeholder, "2"),
            MenuType(R.drawable.ic_placeholder, "3"),
            MenuType(R.drawable.ic_placeholder, "4"),
            MenuType(R.drawable.ic_placeholder, "5"),
            MenuType(R.drawable.ic_placeholder, "6"),
            MenuType(R.drawable.ic_placeholder, "7"),
            MenuType(R.drawable.ic_placeholder, "8"),
            MenuType(R.drawable.ic_placeholder, "9"),
            MenuType(R.drawable.ic_placeholder, "10"),
            MenuType(R.drawable.ic_placeholder, "11"),
            MenuType(R.drawable.ic_placeholder, "12"),
            MenuType(R.drawable.ic_placeholder, "13"),
            MenuType(R.drawable.ic_placeholder, "14"),
            MenuType(R.drawable.ic_placeholder, "15"),
            MenuType(R.drawable.ic_placeholder, "16"),
            MenuType(R.drawable.ic_placeholder, "17"),
            MenuType(R.drawable.ic_placeholder, "18"),
            MenuType(R.drawable.ic_placeholder, "19"),
        )
        menuTypeAdapter = MenuTypeAdapter(context, menuTypeList)
        menu_type_recycler_view.adapter = menuTypeAdapter
        menuTypeAdapter.notifyDataSetChanged()

        hash_tag_menu_recycler_view= view.findViewById(R.id.hash_tag_menu_recycler_view)
        hash_tag_menu_recycler_view.setHasFixedSize(true)
        var hash_tag_layout = GridLayoutManager(context, 1)
        hash_tag_layout.orientation = LinearLayoutManager.HORIZONTAL
        hash_tag_menu_recycler_view.layoutManager = hash_tag_layout
        hashTagList = arrayListOf(
                HashTag("닭밝"),
                HashTag("곱창"),
                HashTag("죽"),
                HashTag("버거"),
                HashTag("마라탕"),
                HashTag("찜닭"),
                HashTag("냉면"),
                HashTag("쌀국수"),
        )
        hashTagAdapter = HashTagAdapter(context, hashTagList)
        hash_tag_menu_recycler_view.adapter = hashTagAdapter
        hashTagAdapter.notifyDataSetChanged()
        Timer().schedule(StartBanner(), 0, 1000)


        return view
    }

    inner class StartBanner : TimerTask() {
        override fun run() {
            val currentPos = banner_layout.findFirstVisibleItemPosition()
            activity?.runOnUiThread {
                banner_recycler_view.scrollToPosition((currentPos + 1) % bannerAdapter.itemCount)
            }

            try {
            } catch (e : InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}