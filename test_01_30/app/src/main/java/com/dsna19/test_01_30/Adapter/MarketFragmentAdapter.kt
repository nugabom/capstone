package com.dsna19.test_01_30.Adapter

import android.content.Context
import android.icu.text.CaseMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MarketFragmentAdapter (
    val fm : FragmentManager,
    lifecycle: Lifecycle)
    : FragmentStateAdapter(fm, lifecycle) {

    var mFragmentList = arrayListOf<Fragment>()
    var mFragmentTitles = arrayListOf<String>()

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(fm : Fragment, title: String) {
        mFragmentList.add(fm)
        mFragmentTitles.add(title)
    }
}