package com.example.myapplication.bookMenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class MenuListRVAdapter(var context: Context, val menuData:MenuData, var menuFragment: MenuFragment):RecyclerView.Adapter<MenuListRVAdapter.Holder>()  {

    var countTexts = ArrayList<TextView>()//메뉴별로 몇개 넣었는지 저장되는 어레이 리스트





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.bookmenu_menubar, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return menuData.menus.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    public fun setMenuCountTVAL(){
        var i = 0
        while (i < countTexts.size){
            countTexts[i].setText(menuFragment.tableMenuList[menuFragment.getNowTableTab()][i].toString())
            i++
        }

    }


    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        public fun bind(pos:Int){
            var menuImage: ImageView = itemView.findViewById(R.id.menuImage)
            menuImage.setBackgroundResource(menuData.menus[pos].menuImage)
            var menuNameTV:TextView = itemView.findViewById(R.id.menuNameTV)
            menuNameTV.setText(menuData.menus[pos].name)
            var menuExpTV:TextView = itemView.findViewById(R.id.menuExpTV)
            menuExpTV.setText(menuData.menus[pos].menuExp)
            var menuPriceTV:TextView = itemView.findViewById(R.id.menuPriceTV)
            menuPriceTV.setText(menuData.menus[pos].price.toString()+"원")

            var menuCountText:TextView = itemView.findViewById(R.id.menuCountText)
            countTexts.add(menuCountText)

            var plusTV : TextView = itemView.findViewById(R.id.plusTV)
            plusTV.setOnClickListener{
                menuFragment.setMenuPlus(pos)
                menuCountText.setText(menuFragment.tableMenuList[menuFragment.getNowTableTab()][pos].toString())
                menuFragment.renewalSelectedMenu()

            }

            var minusTV : TextView = itemView.findViewById(R.id.minusTV)
            minusTV.setOnClickListener{
                menuFragment.setMenuMinus(pos)
                menuCountText.setText(menuFragment.tableMenuList[menuFragment.getNowTableTab()][pos].toString())
                menuFragment.renewalSelectedMenu()
            }



            itemView.setOnClickListener {
                //메뉴 하나 클릭 됐을 때 수행
            }


        }

    }


}