package com.example.myapplication.bookMenu

import com.example.myapplication.R
import java.io.Serializable


//BookTime 액티비티에서 초기화
class MenuData(val sikdangId:Int): Serializable {

    public var menus =  ArrayList<Menu>()

    init{
        setMenuData()
    }


   public fun setMenuData(){
       var ings0 = ArrayList<Ingredient>()
       ings0.add(Ingredient("닭", "국내산"))
       menus.add(Menu("양념치킨", 25000, "달콤한 양념이 발린 치킨",R.drawable.foodimage, ings0))


       var ings1 = ArrayList<Ingredient>()
       ings1.add(Ingredient("파인애플", "하와이"))
       ings1.add(Ingredient("피자", "피자헛"))

       menus.add(Menu("파인애플피자", 19000, "파인애플이 들어간 피자",R.drawable.foodimage, ings1))

       var ings2 = ArrayList<Ingredient>()
       ings2.add(Ingredient("민트", "치약"))
       ings2.add(Ingredient("초코", "가나쬬꼬렛"))
       ings2.add(Ingredient("피자", "도미노피자"))

       menus.add(Menu("민트초코피자", 30000,"민트초코가 들어간 피자", R.drawable.foodimage, ings2))


       var ings3 = ArrayList<Ingredient>()
       ings3.add(Ingredient("보리", "보리쌀"))
       ings3.add(Ingredient("콜라", "펩시"))

       menus.add(Menu("맥콜", 1500, "보리가 들어간 콜라",R.drawable.foodimage, ings3))


       var ings4 = ArrayList<Ingredient>()
       ings4.add(Ingredient("솔잎", "집앞"))
       ings4.add(Ingredient("물", "제주도 맑은샘물"))

       menus.add(Menu("솔의눈", 560, "솔잎이 들어간 물",R.drawable.foodimage, ings4))


       var ings5 = ArrayList<Ingredient>()
       ings5.add(Ingredient("소", "호주산 와규"))
       ings5.add(Ingredient("물", "수돗물"))
       ings5.add(Ingredient("야채", "뒷산"))

       menus.add(Menu("선지국", 12000, "소피로 만든 어쩌고 국",R.drawable.foodimage, ings4))



   }
    //name 메뉴 이름, price 가격, menuExp 메뉴 상세 설명, menuImage: 메뉴 이미지, ingredients: 식재료와 원산지 넣은 ArrayList
    inner class Menu(var name:String, var price:Int, var menuExp:String, var menuImage:Int, var ingredients:ArrayList<Ingredient>): Serializable
    inner class Ingredient(var ing:String, var country:String): Serializable//식재료와 원산지
}