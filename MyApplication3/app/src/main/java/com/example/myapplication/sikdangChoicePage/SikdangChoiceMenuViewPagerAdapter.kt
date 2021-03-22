package com.example.myapplication.sikdangChoicePage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R

//뷰페이저에 프래그먼트 바인드하는 클래스
//프래그먼트 몇개 넣을지, 매뉴의 어댑터 매개변수로 받음
//매뉴 어댑터 받아서 메뉴 어댑터의 스크롤 함수르 호출한다
class SikdangChoiceMenuViewPagerAdapter(fa:FragmentActivity, var sikdangListReqData: SikdangListReqData, val catArrayList : ArrayList<String>, var vp: ViewPager2,
                                        val sikdangChoiceCatAdapter: SikdangChoiceCatAdapter) : FragmentStateAdapter(fa) {

    var isFirst =true


    override fun getItemCount(): Int {
        //페이지수 리턴해야 함
        return catArrayList.size
    }

    public fun setPagerPos(pos:Int){
        vp.setCurrentItem(pos, true)
    }

    override fun createFragment(position: Int): Fragment {
        //프래그먼트 초기화
        //좌표는 지도와 연동해서 구해야 함
        sikdangListReqData.setCat(catArrayList[position])
        var sikdangMenuData = SikdangMenuData(sikdangListReqData)
        sikdangListReqData.setCat(catArrayList[position])
        var sikdangChoiceMenuFragment = SikdangChoiceMenuFragment(sikdangListReqData, vp)
        if (isFirst == true){//카테고리 클릭해서 페이지 들어갔을 때 클릭한 카테고리의 프래그먼트가 제일 처음 뜨게 해준다
            var pos = sikdangChoiceCatAdapter.getCurruntNum()
            vp.setCurrentItem(pos, true)
            isFirst = false
        }
        return sikdangChoiceMenuFragment
    }




}