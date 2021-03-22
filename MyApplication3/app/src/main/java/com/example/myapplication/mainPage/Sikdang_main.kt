package com.example.myapplication.mainPage

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.*
import com.example.myapplication.R
//import com.example.myapplication.bookMenu.MenuData
import com.example.myapplication.dataclass.Banner
import com.example.myapplication.dataclass.HashTag
import com.example.myapplication.dataclass.Location
import com.example.myapplication.recommendation.MsgCat
import com.example.myapplication.sikdangChoicePage.SikdangChoice
import com.google.firebase.database.*
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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

    lateinit var hash_tag_menu_recycler_view: RecyclerView
    lateinit var hashTagAdapter: HashTagAdapter
    lateinit var hashTagList : ArrayList<HashTag>

    lateinit var banner_recycler_view: RecyclerView
    lateinit var bannerAdapter: BannerAdapter
    lateinit var bannerList: ArrayList<Banner>
    lateinit var banner_layout : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sikdang_main)
        //Log.d("종료지점확인", "onCreate_1")
        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()

        /*
        //뷰페이저

        viewPager2=findViewById(R.id.banner_view_pager_2)
        var pagerAdapter2 = ScreenSlidePagerAdapter2(this)
        viewPager2.adapter=pagerAdapter2
*/
        //에딧 텍스트 : 거리
        var editTextDist : EditText = findViewById(R.id.editTextDist)
        editTextDist.imeOptions = EditorInfo.IME_ACTION_DONE
        editTextDist.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(TextUtils.isEmpty(v!!.text.toString())) {
                        Toast.makeText(this@Sikdang_main, "거리 (숫자)를 입력하세요;;", Toast.LENGTH_SHORT).show()
                        return false
                    }
                    val range = v!!.text.toString().toInt()
                    if(range == 0) {
                        Toast.makeText(this@Sikdang_main, "거리 (숫자)를 입력하세요;;", Toast.LENGTH_SHORT).show()
                        v!!.text = ""
                        return false
                    }
                    var intent = Intent(this@Sikdang_main, MapActivity::class.java)
                    intent.putExtra("range", range)
                    startActivity(intent)
                }
                return false
            }
        })
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

        hash_tag_menu_recycler_view= findViewById(R.id.hash_tag_recycler_view)
        hash_tag_menu_recycler_view.setHasFixedSize(true)
        var hash_tag_layout = GridLayoutManager(this, 1)
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
            HashTag("쌀국수")
        )

        hashTagAdapter = HashTagAdapter(this, hashTagList)
        hash_tag_menu_recycler_view.adapter = hashTagAdapter
        hashTagAdapter.notifyDataSetChanged()
        Log.d("종료지점확인", "167")

        banner_recycler_view = findViewById(R.id.banner_recycler_view)
        banner_recycler_view.setHasFixedSize(true)
        banner_layout = LinearLayoutManager(this)
        banner_layout.orientation = LinearLayoutManager.HORIZONTAL
        banner_recycler_view.layoutManager = banner_layout
        bannerList = arrayListOf(
            Banner(R.drawable.ic_placeholder, "1"),
            Banner(R.drawable.add_main, "2"),
            Banner(R.drawable.add_main_2, "3"),
            Banner(R.drawable.add_main_3, "4"),
            Banner(R.drawable.add_main_4, "5"),
            Banner(R.drawable.add_main_5, "6"),
            Banner(R.drawable.add_main_6, "7")
        )
        bannerAdapter = BannerAdapter(this, bannerList)
        banner_recycler_view.adapter = bannerAdapter
        bannerAdapter.notifyDataSetChanged()

        Timer().schedule(StartBanner(), 0, 1000)
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

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getKeyHashBase64(context : Context) {
        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        val signatures = info.signingInfo.apkContentsSigners
        val md = MessageDigest.getInstance("SHA")
        for (signature in signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val key = String(Base64.encode(md.digest() , 0))
            Log.d("hash key", key.toString())
        }
    }
/*
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
*/


    inner class StartBanner : TimerTask() {
        override fun run() {
            val currentPos = banner_layout.findFirstVisibleItemPosition()
            runOnUiThread {
                banner_recycler_view.scrollToPosition((currentPos + 1) % bannerAdapter.itemCount)
            }

            try {
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }}
/*
    class RestaurantMenu (
            var restaurant_id: String,
            var menu : MenuData._menu,
            var ingredients: ArrayList<MenuData.Ingredient>
    ) {
        fun updateDB() {
            lateinit var menuRef : DatabaseReference
            if (restaurant_id == "NOT") {
                menuRef = FirebaseDatabase.getInstance().getReference("Restaurants")
                        .child("한식")
                        .push()
                        .child("menu")
                        .push()
            } else {
                menuRef = FirebaseDatabase.getInstance().getReference("Restaurants")
                        .child("한식")
                        .child(restaurant_id)
                        .child("menu")
                        .push()
            }

            var menu__ = hashMapOf<String, Any>(
                    "product" to menu.product!!,
                    "price" to menu.price!!,
                    "product_exp" to menu.product_exp!!
            )
            menuRef.setValue(menu__)
            var ingredientRef = menuRef.child("ingredients").ref
            for (ingredient in ingredients) {
                var ing = hashMapOf<String, Any>(
                        "ing" to ingredient.ing!!,
                        "country" to ingredient.country!!
                )
                ingredientRef.push().setValue(ing)
            }
        }
    }
}
fun makeMenuDB (){
    var DB = arrayListOf<Sikdang_main.RestaurantMenu>(
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("고기듬뿍김치찌개 도시락",	8500,	"고기듬뿍김치찌개1인분+도시락(흰쌀밥+계란후라이+반찬3종)"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("묵은지김치찜 도시락",	9500,	"묵은지김치찜1인분+도시락(흰쌀밥+계란후라이+반찬3종)"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("묵은지김치찜 (소)",	19000,	"묵은지김치찜(소)+흰쌀밥2+다섯가지반찬1set"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("묵은지김치짐 (중)",	26000,	"묵은지김치찜(중)+흰쌀밥3+다섯가지반찬2set"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("치즈김치찜 (소)",	22000,	"치즈김치찜(소)+흰쌀밥2+다섯가지반찬1set"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("치즈김치찜 (중)",	29000,	"치즈김치찜(중)+흰쌀밥3+다섯가지반찬2set"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("고기듬뿍김치찌개 (소)",	18000,	"고기듬뿍김치찌개(소)+흰쌀밥2+다섯가지반찬1set"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("고기듬뿍김치찌개 (중)",	25000,	"고기듬뿍김치찌개(중)+흰쌀밥3+다섯가지반찬2set"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("묵은지김치찜 (소)+제육불고기(200)g",	23000,	"묵은지김치찜(소)+제육불고기(200g)+공기밥2+반찬1s"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("묵은지김치찜 (중)+제육불고기(200)g",	30000,	"묵은지김치찜(중)+제육불고기(200g)+공기밥3+반찬2s"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("고기듬뿍김치찌개 (소)+제육불고기200g",	22000,	"김치찌개(소)+제육불고기(200g)+공기밥2+반찬1s"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("고기듬뿍김치찌개 (중)+제육불고기200g",	29000,	"김치찌개(중)+제육불고기(200g)+공기밥3+반찬2s"
            ), arrayListOf(MenuData.Ingredient("돈육",	"국내산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("고기반김치반 왕만두",	6000,	"고기왕만두3 + 김치왕만두3"
            ), arrayListOf(MenuData.Ingredient("돈육",	"국내산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("육즙가득 고기왕만두",	5000,	 "육즙가득 고기왕만두 5개"
            ), arrayListOf(MenuData.Ingredient("돈육",	"국내산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("아삭한 김치왕만두",	5000,	"김치왕만두 5개"
            ), arrayListOf(MenuData.Ingredient("음료",	"캐나다산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("달콤한 갈비만두",	5000,	"갈비만두 5개"
            ), arrayListOf(MenuData.Ingredient("돈육",	"국내산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("투명한 매콤 쭈꾸미만두",	5000,	"쭈꾸미만두 8개"
            ), arrayListOf(MenuData.Ingredient("쭈구미",	"베트남산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("바삭한 군만두",	5000,	"바삭한 군만두 6개"
            ), arrayListOf(MenuData.Ingredient("돈육",	"국내산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("계란찜말이",	5000,	"고급 가쓰오부시 토핑"
            ), arrayListOf(MenuData.Ingredient("계란",	"국내산"))),
            Sikdang_main.RestaurantMenu("-MVqFt4d2dHMBteR9XcO",MenuData._menu("제육불고기(200g)",	6000,	"제육 200g"
            ), arrayListOf(MenuData.Ingredient("돈육",	"미국산")))

            )

    for(data in DB){
        data.updateDB()
    }
}

fun makeLocationsDB(){
    Log.d("makeLo", "here")
    val cat = "닭고기"
    var locationRef = FirebaseDatabase.getInstance().getReference("Locations")
            .child(cat)


    var DB = arrayListOf(
            Location("와우빌딩", 37.535512, 126.824337),
            Location("짜투리 닷컴", 37.535677, 126.825981),
            Location("신월중학교", 37.536663, 126.824309),
            Location("계레얼학교", 37.535352, 126.826523),
            Location("광음교회", 37.535789, 126.826523),
            Location("제이씨빌딩", 37.535776, 126.826325),
            Location("흡연장", 37.535879, 126.825058)
    )

    for (data in DB){
        var pushed = locationRef.push()
        val key = pushed.key
        var map = makeData(data)
        map.put("id", key!!)
        Log.d("map",map.toString())
        pushed.setValue(map)
    }

}

fun makeData(loc : Location) : HashMap<String, Any> {
    Log.d("dsds", "sdsds")
    return hashMapOf<String, Any>(
            "name" to loc.name,
            "Lat" to loc.Lat,
            "Lng" to loc.Lng
    )
}
*/