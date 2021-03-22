package com.example.myapplication.mainPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.*
import com.example.myapplication.recommendation.MsgCat

private const val NUM_PAGE = 5
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Sikdang_main : AppCompatActivity() {
    //음식 추천 태그
    var tagList_sm = arrayListOf<TagLine>();
    //배너 뷰페이저
    private lateinit var viewPager2: ViewPager2
    //배너 페이지의 수
    private var num_page = 5

    var msgCat = MsgCat()
    var tagList : Array<String> = msgCat.getTagList()
    //var tagLineList : TagLineList = TagLineList();
    var tagLineList : TagLineList = TagLineList(tagList);




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sikdang_main)
        //Log.d("종료지점확인", "onCreate_1")
        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()

        //뷰페이저

        viewPager2=findViewById(R.id.banner_view_pager_2)
        var pagerAdapter2 = ScreenSlidePagerAdapter2(this)
        viewPager2.adapter=pagerAdapter2

        //에딧 텍스트 : 거리
        var editTextDist : EditText = findViewById(R.id.editTextDist)
        //editTextDist.editableText

        //음식 카테고리
        //어댑터에서 SikdangChoice 페이지를 불러오게 된다
        //Log.d("종료지점확인", "onCreate_2")
        var catList=CatList()
        //Log.d("종료지점확인", "onCreate_3")
        var dist :Int = 0
        //dist = editTextDist.getText().toString().toInt()
        //Log.d("종료지점확인", "onCreate_3.5 "+dist.toString())
        var sikdangMainCatAdapter=SikdangMainCatAdapter(this, catList.getCatArray(), editTextDist)
        //Log.d("종료지점확인", "onCreate_4")
        var sikdangCatView : RecyclerView = findViewById(R.id.sikdang_cat_view)
        //Log.d("종료지점확인", "onCreate_5")
        sikdangCatView.adapter = sikdangMainCatAdapter
        //Log.d("종료지점확인", "onCreate_6")

        var catLM = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sikdangCatView.layoutManager=catLM
        sikdangCatView.setHasFixedSize(true)






        //오늘 뭐먹지? 누르면 페이지 호출
        //여기에 눌러진 버튼도 매개변수로 줘야함
        var button_call_what_eat_today: Button = findViewById(R.id.button_whatEatToday)

        //putExtra로 넘겨주는 객체는 원본 객체에 접근하게 하는 것이 아닌 constructor에 있는 부분만 복사한 객체 새로 생성
        //오늘 뭐먹지 버튼 누르면 msgCat 객체를 whatEatToday 클래스로 넘겨준다
        button_call_what_eat_today.setOnClickListener{
            val intent=Intent(this, What_eat_today::class.java)
            var tempText="asasa";
            tempText+=msgCat.getText()
            Log.d("확인 putExtra", tempText)
            intent.putExtra("msgcat", msgCat)
            Log.d("종료지점확인", "call What_eat_today")
            startActivity(intent)
        }


        //음식 추천 태그
        if(true){
            tagList_sm=tagLineList.getTagLineList()
            var tag_Adapter= Sikdang_main_tagAdapter(this, tagList_sm, msgCat)
            var main_tagList: RecyclerView = findViewById(R.id.main_tagList);

            main_tagList.adapter=tag_Adapter;

            var tag_lm= LinearLayoutManager(this)
            main_tagList.layoutManager=tag_lm;
            main_tagList.setHasFixedSize(true)

        }


        Log.d("종료지점확인", "167")


    }





    /*

    //백 버튼

    override fun onBackPressed() {
        if (viewPager2.currentItem==0){
            super.onBackPressed()
        }else{
            viewPager2.currentItem=viewPager2.currentItem-1
        }
    }*/

    //배너 어댑터
    //최대 12페이지까지=> 진짜 12페이지까지 하려면 배너이미지 추가하고 아래 코드 좀 수정해야함
    private inner class ScreenSlidePagerAdapter2(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = num_page

        override fun createFragment(position: Int): Fragment {
            if (position == 0){
                return BannerSlideFragment(R.drawable.add_main, 0)
            }else if(position == 1){
                return BannerSlideFragment(R.drawable.add_main_2, 1)
            }else if(num_page >=3 && position == 2){
                return BannerSlideFragment(R.drawable.add_main_3, 2)
            }else if(num_page >=4 && position == 3){
                return BannerSlideFragment(R.drawable.add_main_4, 3)
            }else if(num_page >=5 && position == 4){
                return BannerSlideFragment(R.drawable.add_main_5, 4)
            }else if(num_page >=6 && position == 5){
                return BannerSlideFragment(R.drawable.add_main_6, 5)
            }else if(num_page >=7 && position == 6){
                return BannerSlideFragment(R.drawable.add_main_6, 6)
            }else if(num_page >=8 && position == 7){
                return BannerSlideFragment(R.drawable.add_main_6, 7)
            }else if(num_page >=9 && position == 8){
                return BannerSlideFragment(R.drawable.add_main_6, 8)
            }else if(num_page >=10 && position == 9){
                return BannerSlideFragment(R.drawable.add_main_6, 9)
            }else if(num_page >=11 && position == 10){
                return BannerSlideFragment(R.drawable.add_main_6, 10)
            }else if(num_page >=12 && position == 11){
                return BannerSlideFragment(R.drawable.add_main_6, 11)
            }else{
                return BannerSlideFragment(R.drawable.add_main, 0)
            }

        }


    }



}
