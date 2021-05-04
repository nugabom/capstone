package com.example.myapplication.sikdangChoicePage

import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//리사이클러뷰에 어댑터 설정하는 클래스
//SikdangChoiceMenuViewPagerAdapter 클래스의 creatFragment 에서 사용
class SikdangChoiceMenuFragment(var callback: SikdangChoice, var vp:ViewPager2, var catory : String) :Fragment() {
    var sikdangList : ArrayList<SikdangListReqData> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        //view 는 리사이클러뷰 하나 들어있는 레이아웃
        var view= inflater.inflate(R.layout.sikdangchoice_menu_fragment, container, false)
        bind(view)

        //View.setBackgroundResource(bannerImage)

        return view
    }



    fun bind(itemView:View):View?{
        //Log.d("확인 SikdangChoiceMenuFragment", "bind")
        //itemView는 프래그먼트 자체
        //var sikdangchoice_menuline : LinearLayout = itemView.findViewById(R.id.sikdangChoiceMenuFragmentRecyclerView)
        //itemView.setCurrentItem(1)
        var sikdangChoiceMenuFragmentRecyclerView : RecyclerView = itemView.findViewById(R.id.sikdangChoiceMenuFragmentRecyclerView)
        var sikdangChoiceMenuAdapter = SikdangChoiceMenuAdapter(requireContext(), arrayListOf<SikdangListReqData>())
        sikdangChoiceMenuFragmentRecyclerView.adapter = sikdangChoiceMenuAdapter

        var sikdangChice_catLineLM = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        sikdangChoiceMenuFragmentRecyclerView.layoutManager=sikdangChice_catLineLM
        sikdangChoiceMenuFragmentRecyclerView.setHasFixedSize(true)

        //Log.e("확인 페이지 넘기기 전", "1")
        //vp.setCurrentItem(10, true)
        //Log.e("확인 페이지 넘김", "1")
        //vp.setCurrentItem(4, true)
        //Log.e("확인 페이지 넘김", "2")


        return itemView
    }

    private fun getFromDB() {

    }
}