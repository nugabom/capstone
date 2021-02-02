package com.example.myapplication

import android.R.attr.data
import android.content.ClipData
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListAdapter
import android.widget.LinearLayout
import java.util.EnumSet.range

class baedal_main_banner: AppCompatActivity() {

/*
    private var mainBanner: LinearLayout=findViewById(R.id.banner_ad_item);


    private var bannerNum =3;

    private var i = 0;
    private var bannerButtonParams=LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
    );

    public fun setBanner(){
        /*
        while (i<bannerNum) {
            var addBanner= Button(this);
            addBanner.layoutParams = bannerButtonParams;
            addBanner.setBackgroundResource(R.drawable.add_main);
            mainBanner.addView(addBanner);
         */
        var mainBanner: LinearLayout =findViewById(R.id.banner_ad);
        var bannerNum =3;

        var i = 0;
        var bannerButtonParams=LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
        while(i<bannerNum){
            var addBanner= Button(this);
            addBanner.layoutParams = bannerButtonParams;
            addBanner.setBackgroundResource(R.drawable.add_main);
            mainBanner.addView(addBanner);

            i++;
        }
        }
    }


    /*
    while(i<bannerNum){
        var addBanner= Button(this);
        addBanner.layoutParams = bannerButtonParams;
        addBanner.setBackgroundResource(R.drawable.add_main);
        mainBanner.addView(addBanner);

        i++;
    }
    */


*/

}