package com.example.myapplication

import android.R.attr.data
import android.content.ClipData
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.baedal_main)

        /// 메인 배너
        if (true) {
            var mainBanner: LinearLayout = findViewById(R.id.banner_ad);
            var bannerNum = 3;

            var i = 0;
            var bannerButtonParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            )
            while (i < bannerNum) {
                var addBanner = Button(this);
                addBanner.layoutParams = bannerButtonParams;
                addBanner.setBackgroundResource(R.drawable.add_main);
                mainBanner.addView(addBanner);

                i++;
            }
        }

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






}



