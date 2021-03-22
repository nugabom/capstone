package com.example.myapplication.bookTime

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.database.*
import java.io.Serializable

class MenuBuilder(val sikdangId: String, val category : String) {
    var Menus = arrayListOf<Menu>()
    fun build() {
        var MenuInfoRference = FirebaseDatabase.getInstance().getReference("Restaurants")
                .child(category)
                .child(sikdangId)
                .child("menu")

        MenuInfoRference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                getMenuFromDB(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    fun getMenuFromDB(menus_snapshot : DataSnapshot) {
        var last = menus_snapshot.childrenCount.toInt()
        for (menu_data in menus_snapshot.children) {
            var ingredientReference = menu_data.ref.child("ingredients")
            val menu = menu_data.getValue(_menu::class.java)
            Log.d("menuInfoReference", "menu: ${menu!!.product}, ${menu!!.price}, ${menu!!.product_exp}")

            ingredientReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(ingredients_snapshot: DataSnapshot) {
                    val ingredients = arrayListOf<Ingredient>()
                    for (ingredient_data in ingredients_snapshot.children){
                        var ingredient = ingredient_data.getValue(Ingredient::class.java)
                        Log.d("IngInfoReference", "ing: ${ingredient!!.ing}, ${ingredient!!.country}")
                        ingredients.add(ingredient!!)
                    }
                    Menus.add(Menu(menu, R.drawable.food_placeholder, ingredients))
                    --last

                    // Synchroized
                    if(last == 0) {
                        for (menu in Menus) {
                            for (ing in menu.ingredients) {
                                Log.d(menu.name, "${ing.ing} : 원산지 ${ing.country}")
                            }
                        }

                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }

    inner class Menu(var name:String, var price:Int, var menuExp:String, var menuImage:Int, var ingredients:ArrayList<Ingredient>): Serializable {
        constructor(menu : _menu, menuImage: Int, ingredients: ArrayList<Ingredient>):
                this(menu.product!!, menu.price!!, menu.product_exp!!, menuImage, ingredients)
    }

    @IgnoreExtraProperties
    data class _menu(val product: String? = null, val price: Int? = null, val product_exp: String? = null){}

    data class Ingredient(val ing:String? = null, val country:String? = null)//식재료와 원산지
}