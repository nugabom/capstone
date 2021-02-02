package com.dsna19.test_01_30

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.dsna19.test_01_30.Adapter.MarketFragmentAdapter
import com.dsna19.test_01_30.Fragment.MarketFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SelectMarketActivity : AppCompatActivity() {
    lateinit var view_pagers : ViewPager2
    lateinit var tabs : TabLayout
    lateinit var mViewAdapter: MarketFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_market)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        tabs = findViewById(R.id.tabs)
        view_pagers = findViewById(R.id.view_pagers)
        mViewAdapter = MarketFragmentAdapter(supportFragmentManager, lifecycle)

        mViewAdapter.addFragment(MarketFragment("1"), "Fragment 1")
        mViewAdapter.addFragment(MarketFragment("2"), "Fragment 2")
        mViewAdapter.addFragment(MarketFragment("3"), "Fragment 3")
        mViewAdapter.addFragment(MarketFragment("4"), "Fragment 4")
        mViewAdapter.addFragment(MarketFragment("5"), "Fragment 5")

        view_pagers.adapter = mViewAdapter

        TabLayoutMediator(tabs, view_pagers) { tab, position ->
            tab.text = mViewAdapter.mFragmentTitles[position]
        }.attach()
    }
}