package com.example.myapplication.sikdangChoicePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.MapActivity
import com.example.myapplication.R
import com.example.myapplication.mainPage.CatList

//SikdangMainCatAdapter에서 쓰임
//선택된 카테고리, 카테고리 리스트, 거리를 받음
class SikdangChoice : AppCompatActivity() {
    //리스트는 SikdangChoiceCatAdapter 클래스의 inner class인 Holder 클래스의 bind()함수에서 칵 카테고리의 toggle 버튼으로 채워준다
    var sikdangChoice_toggleButton_arrayList = ArrayList<ToggleButton>()
    private lateinit var sikdangChoiceMenuViewPager : ViewPager2//메뉴부분 뷰페이저

    lateinit var sikdangChoice_distET : EditText
    lateinit var range_from_text : EditText
    lateinit var find_from_map : Button
    lateinit var find_from_text : Button
    var selectedCat : String?  = null

    var isFirst = true
    var range2 : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var menuPagerAdapter : SikdangChoiceMenuViewPagerAdapter

        setContentView(R.layout.sikdangchoice)
        sikdangChoiceMenuViewPager = findViewById(R.id.sikdangChoiceMenuViewPager)
        Log.d("종료지점확인 SikdangChoice", "레이아웃 호출")
        initUi()

        //msg객체를 넘겨받는다.
        var intent = getIntent()
        selectedCat = intent.getExtras()?.getString("cat")
        //카테고리의 이름들 ArrayList 형태로 넘겨받는다 (소고기, 돼지고기, 닭고기....)
        var catArrayList = CatList().getCatArray()
        if(selectedCat == null) selectedCat = catArrayList[0]
        //var sikdangMainET:EditText = intent.getExtras("a")
        var dist:Int? = 0
        dist = intent.getExtras()?.getInt("dist")
        if(dist == null) { dist = 1000 }
        range2 = dist

        //넘겨주는 데이터 생성 dist 넣음

        Log.d("종료지점확인 SikdangChoice", "intent 받음")
        //일단 배열을 비어있는 토글버튼으로 채운다
        var i = 0
        while(i< catArrayList?.size!!) {
            sikdangChoice_toggleButton_arrayList.add(findViewById(R.id.sikdangchice_toggleButton))
            i++
        }

        //catLine 어댑터 사용한다

        var sikdangChoice_CatLine : RecyclerView = findViewById(R.id.sikdangChoice_catLine)
        var sikdangChoiceCatAdapter = SikdangChoiceCatAdapter(this, catArrayList, sikdangChoice_toggleButton_arrayList, selectedCat!!, sikdangChoice_CatLine)
        Log.d("종료지점확인 SikdangChoice", "sikdangChoiceCatAdapter")
        //sikdangChoiceCatAdapter.setHasStableIds(true)
        Log.d("종료지점확인 SikdangChoice", "sikdangChoice_CatLine")


        sikdangChoice_CatLine.adapter = sikdangChoiceCatAdapter
        Log.d("종료지점확인 SikdangChoice", "sikdangChoice_CatLine.adapter = sikdangChoiceCatAdapter")

        var sikdangChice_catLineLM = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sikdangChoice_CatLine.layoutManager=sikdangChice_catLineLM
        sikdangChoice_CatLine.setHasFixedSize(true)


        //거리 에딧텍스트
        var sikdangChoice_distET:EditText = findViewById(R.id.sikdangChoice_distET)
        sikdangChoice_distET.setText(dist.toString())

        //mnu 뷰페이저 fragment 설정

        menuPagerAdapter = SikdangChoiceMenuViewPagerAdapter(this, this, catArrayList, sikdangChoiceMenuViewPager, sikdangChoiceCatAdapter)
        sikdangChoiceCatAdapter.setVPAdapter(menuPagerAdapter)
        sikdangChoiceMenuViewPager.adapter = menuPagerAdapter
        //sikdangChoiceMenuViewPager.registerOnPageChangeCallback()
        sikdangChoiceMenuViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(position >= sikdangChoiceMenuViewPager!!.adapter!!.itemCount) return
                //Log.e("확인 페이지 넘긴 후", "1")
                super.onPageSelected(position)
                //Log.e("확인 ViewPagerFragment", "Page ${position+1}")
                sikdangChoiceCatAdapter.scrollPosition(position)
                //Log.e("확인 페이지 넘긴 후", "2")
                if (isFirst == false){
                    //Log.e("확인 페이지 넘긴 후", "toggleOn")
                    sikdangChoiceCatAdapter.toggleOn(position)
                }
                //Log.e("확인 페이지 넘긴 후", "3")
                isFirst = false

            }
        })
        //sikdangChoiceMenuViewPager.setCurrentItem(1, true)
    }

    private fun initUi() {
        sikdangChoice_distET = findViewById(R.id.sikdangChoice_distET)
        sikdangChoice_distET.imeOptions = EditorInfo.IME_ACTION_DONE
        sikdangChoice_distET.setOnEditorActionListener (object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    val dist = sikdangChoice_distET.text.toString().toInt()
                    var intent = Intent(this@SikdangChoice, MapActivity::class.java)
                    intent.putExtra("range", dist)
                    range2 = dist
                    startActivity(intent)
                    return true
                }
                return false
            }
        })

        find_from_map = findViewById(R.id.find_from_map)
        find_from_map.setOnClickListener {
            val dist = sikdangChoice_distET.text.toString().toInt()
            var intent = Intent(this@SikdangChoice, MapActivity::class.java)
            intent.putExtra("range", dist)
            range2 = dist
            startActivity(intent)
        }

        range_from_text = findViewById(R.id.range_from_text)
        range_from_text.imeOptions = EditorInfo.IME_ACTION_DONE
        range_from_text.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val dist = range_from_text.text.toString().toInt()
                    range2 = dist
                    return true
                }
                return false
             }
        })

        find_from_text = findViewById(R.id.find_from_text)
        find_from_text.setOnClickListener {
            val dist = range_from_text.text.toString().toInt()
            range2 = dist
        }
    }
}