package com.dsna19.test2

import android.R.attr.*
import android.content.ClipData
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class baedal_main : AppCompatActivity() {
    var tagList_bm = arrayListOf<TagLine>();
    //var main_tagList= findViewById<RecyclerView>(R.id.main_tagList);
    //var main_tagList: RecyclerView = findViewById(R.id.main_tagList);
    var banner_items = arrayListOf<Int>(
            R.drawable.add_main,
            R.drawable.add_main_2,
            R.drawable.add_main_3,
            R.drawable.add_main_4
    )
    lateinit var mainBanner : LinearLayout
    lateinit var addBanner : Button
    val activateBanner = ActivateBanner()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.baedal_main)

        /// 메인 배너
        var mainBanner : LinearLayout = findViewById(R.id.banner_ad)
        var bannerButtonParaams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        addBanner = Button(this)
        addBanner.layoutParams = bannerButtonParaams
        addBanner.setBackgroundResource(R.drawable.add_main)
        mainBanner.addView(addBanner)
        activateBanner.start()

        //음식 추천 태그
        if(true){
            var tagLineList : TagLineList= TagLineList();
            tagList_bm=tagLineList.getTagLineList()
            var tag_Adapter=Baedal_main_tagAdapter(this, tagList_bm)
            var main_tagList: RecyclerView = findViewById(R.id.main_tagList);

            main_tagList.adapter=tag_Adapter;

            var tag_lm= LinearLayoutManager(this)
            main_tagList.layoutManager=tag_lm;
            main_tagList.setHasFixedSize(true)

        }
    }

    inner class ActivateBanner : Thread(){
        override fun run() {
            for (src in banner_items) {
                runOnUiThread {
                    addBanner.setBackgroundResource(src)
                }
                Thread.sleep(8000)
            }
            this.start()
        }
    }
}



