package com.dsna19.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var tabs : TabLayout
    lateinit var viewPager : ViewPager2

    lateinit var fragmentList: ArrayList<Fragment>
    lateinit var stringList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabs = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.viewPager)

        fragmentList = arrayListOf()
        stringList = arrayListOf()

        var viewPagerAdapter  = ViewPagerAdapter(supportFragmentManager,fragmentList, stringList, lifecycle)
        viewPagerAdapter.addFragment(Main_OneFragment(), "Main_1_Fragment")
        viewPagerAdapter.addFragment(Main_TwoFragment(), "Main_2_Fragment")
        viewPagerAdapter.addFragment(Main_ThreeFragment(), "Main_3_Fragment")
        viewPagerAdapter.addFragment(Main_FourthFragment(), "Main_4_Fragment")
        viewPagerAdapter.addFragment(Main_FifthFragment(), "Main_5_Fragment")

        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = stringList[position]
            tab.view.
        }.attach()

    }

    class ViewPagerAdapter(fm : FragmentManager,
                           var fragmentList : ArrayList<Fragment>,
                           var stringList : ArrayList<String>,
                           lifecycle: Lifecycle
                           ) : FragmentStateAdapter(fm, lifecycle)
    {

        fun addFragment(fragment: Fragment, title : String) {
            fragmentList.add(fragment)
            stringList.add(title)
        }

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}