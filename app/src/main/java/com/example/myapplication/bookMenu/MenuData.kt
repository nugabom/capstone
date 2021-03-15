package com.example.myapplication.bookMenu

import android.util.Log
import com.example.myapplication.R
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.io.Serializable


// BookTime 액티비티에서 초기화
// 아직 미구현
class MenuData(val sikdangId:Int): Serializable {

    public var menus =  ArrayList<Menu>()

    init{
        setMenuData()
    }


    private fun getFromDB(sikdangId: Int) {
        var _menus = arrayListOf<_menu>()
        var total_ingredients = arrayListOf<ArrayList<Ingredient>>()

        var menuInfoReference = FirebaseDatabase.getInstance().getReference("Restaurants")
                .child("000001")
                .child("menu")

        menuInfoReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(menus_snapshot: DataSnapshot) {
                for (menu_data in menus_snapshot.children) {
                    var ingredients = arrayListOf<Ingredient>()
                    var ingredientReference = menu_data.ref.child("ingredients")

                    val menu = menu_data.getValue(_menu::class.java)
                    Log.d("menuInfoReference", "menu: ${menu!!.product}, ${menu!!.price}, ${menu!!.product_exp}")
                    if (menu != null) {
                        _menus.add(menu)
                    }

                    ingredientReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(ingredients_snapshot: DataSnapshot) {
                            for (ingredient_data in ingredients_snapshot.children){
                                var ingredient = ingredient_data.getValue(Ingredient::class.java)
                                Log.d("IngInfoReference", "ing: ${ingredient!!.ing}, ${ingredient!!.country}")
                                ingredients.add(ingredient!!)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("getFromDB", "${sikdangId}: menu 정보받는데 실패")
            }
        })

    }

    public fun setMenuData(){
        getFromDB(0)
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

    @IgnoreExtraProperties
    data class _menu(val product: String? = null, val price: Int? = null, val product_exp: String? = null){}

    data class Ingredient(val ing:String? = null, val country:String? = null)//식재료와 원산지
}