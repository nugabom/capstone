package com.example.myapplication.bookMenu

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.payPage.PayPage

class MenuTableRVAdapter(var context: Context, var menuData:MenuData, val bookTableNum:Int,
                         val tableArrayList:ArrayList<ArrayList<Int>>, val floorList:ArrayList<Int>,val fAndTAL : ArrayList<Int>,  var menuFragment:MenuFragment) : RecyclerView.Adapter<MenuTableRVAdapter.Holder>()  {

    //var fAndTAL = ArrayList<Int>()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.d("확인 MenuTableRVAdapter", "생성")
        val view = LayoutInflater.from(context).inflate(R.layout.bookmenu_eachtable, parent, false)
        //alToAL()
        var i = 0
        var tempAL = ArrayList<Boolean>()
        while(i < bookTableNum){//isTableBooked false로 초기화
            tempAL.add(false)
            i++
        }
        menuFragment.isTableBooked=tempAL
        return Holder(view)
    }

    override fun getItemCount(): Int {//리사이클러뷰에 몇개 들어갈것인가 : 테이블 수만큼
        return bookTableNum
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }

    /*
    public fun alToAL(){
        //var floor = floorList[0]


        var i = 0
        var tempString = ""
        while (i < tableArrayList.size) {
            var floor = floorList[i]
            var j = 0
            var tempAR = ArrayList<Int>()
            while (j < tableArrayList[i].size) {
                if(tableArrayList[i][j] !=0) {
                    fAndTAL.add(floor)
                    tempString += floor.toString()
                    //fAndTAR.add(tableArrayList[i][j])
                    fAndTAL.add(j)
                    tempString += j.toString()
                }
                j++
            }
            i++
        }
        Log.d("확인 층수배열확인", tempString)

    }*/

    inner class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
        public fun bind(pos:Int){
            Log.d("확인 MenuTableRVAdapter", "Holder.bind")
            //테이블 번호
            var tableNumTV:TextView = itemView.findViewById(R.id.tableNumTV)
            tableNumTV.setText(fAndTAL[(pos*2)].toString()+"층 테이블"+fAndTAL[pos*2+1].toString())
            //Log.d("확인 MenuTableRVAdapter", "Holder.bind2")

            var tableMenuRV : RecyclerView = itemView.findViewById(R.id.tableMenuRV)
            //Log.d("확인 MenuTableRVAdapter", "Holder.bind2.1")
            var innerRVAdapter = InnerRVAdapter(context, pos)
            //Log.d("확인 MenuTableRVAdapter", "Holder.bind2.2")
            tableMenuRV.adapter = innerRVAdapter
            //Log.d("확인 MenuTableRVAdapter", "Holder.bind3")

            var RVLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            tableMenuRV.layoutManager=RVLayoutManager
            tableMenuRV.setHasFixedSize(true)
            //eachtableLayout

            itemView.setOnClickListener(){//테이블 선택시 : 특정테이블 선택 - 여기에서 저장될 병수 : AllayList 등 지정
                //Log.d("확인 MenuTableRVAdapter", "리사이클러뷰 클릭시")




                menuFragment.setTableText(fAndTAL[(pos*2)], fAndTAL[pos*2+1])
                menuFragment.setNowTableTab(pos)
                menuFragment.turnMenuCount()
            }

        }


        //리사이클러뷰 내부의 리사이클러뷰 어댑터
        //여기서는 각 테이블마다 어떤메뉴 골랐는지 바인드함 -> 실시간ㅁ으로 리사이클러뷰에 올라가야 한다
        //-> 함수 추가해야겠네

        inner class InnerRVAdapter(var innerContext: Context, var tableNum:Int): RecyclerView.Adapter<InnerRVAdapter.InnerHolder>(){

            var selectedMenuCount = 0//선택된 메뉴 종류의 수
            var selectedMenuNums = ArrayList<Int>()//이 테이블에 선택된 메뉴의 번호들 0개이면 그냥 안들어간다



            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerRVAdapter.InnerHolder {
                Log.d("확인 MenuTableRVAdapter.innerRVAdapter", "생성")
                //여기서 레이아웃은 메뉴 한줄이 들어가야 함
                val innerView = LayoutInflater.from(innerContext).inflate(R.layout.bookmenu_selectedline, parent, false)
                return InnerHolder(innerView)
            }

            //여기에는 각 테이블에 예약된 메뉴 몇종류인가가 들어가야 함
            //미리 상위에서 받아오는게 좋을듯 함
            override fun getItemCount(): Int {
                setSelectedMenuNum()
                if (selectedMenuCount==0){
                    menuFragment.isTableBooked[tableNum] = false
                }
                else{
                    menuFragment.isTableBooked[tableNum] = true
                }
                return selectedMenuCount
            }

            override fun onBindViewHolder(holder: InnerHolder, position: Int) {
                holder.innerBind(position)
            }

            public fun setSelectedMenuNum(){
                var i = 0
                var sMC = 0
                var tempAL = ArrayList<Int>()
                while (i<menuFragment.menuData.menus.size){//모든 메뉴에 대해 루프
                    if(menuFragment.tableMenuList[tableNum][i] != 0){//메뉴가 0개 선택된게 아니면
                        tempAL.add(i)
                        sMC += 1
                    }
                    i++
                }
                selectedMenuNums = tempAL
                selectedMenuCount = sMC
            }


            inner class InnerHolder(var innerView:View):RecyclerView.ViewHolder(innerView){
                public fun innerBind(innerPos:Int){
                    //Log.d("확인 MenuTableRVAdapter.innerRVAdapter  innerBind", "생성")
                    var menuName:TextView = innerView.findViewById(R.id.menuNameLineTV)
                    var menuNum:TextView = innerView.findViewById(R.id.menuNumLineTV)

                    menuName.setText(menuData.menus[selectedMenuNums[innerPos]].name)//이 메뉴의 이름
                    menuNum.setText(menuFragment.tableMenuList[tableNum][selectedMenuNums[innerPos]].toString())//이 메뉴 얼마나 골랐는지
                    //Log.d("확인 MenuTableRVAdapter.innerRVAdapter  innerBind", innerPos.toString()+menuData.menus[selectedMenuNums[innerPos]].name+ menuFragment.tableMenuList[tableNum][innerPos].toString())

                }

            }

        }

    }
}