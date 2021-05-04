package com.example.myapplication.sikdangChoicePage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

//상단 토글버튼
class SikdangChoiceCatAdapter(var context : Context, val catArrayList: ArrayList<String>?,
                              var sikdangChoice_toggleButton_arrayList: ArrayList<ToggleButton>,
                              var selectedCat :String,
                              var sikdangChoiceCatLine : RecyclerView) : RecyclerView.Adapter<SikdangChoiceCatAdapter.Holder>() {
    var catArrayListSize = catArrayList?.size!!
    var toggleArrayList= Array<Boolean>(catArrayListSize, {false})
    var firstCatCount = getCount(selectedCat) // 처음 들어온 카테고리의 위치
    var lastBindCount = 0 //현재 바인드 되어있는 최대 숫자
    var isFirst = true //처음 클린한 것인가
    lateinit var vpAdapter : SikdangChoiceMenuViewPagerAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        //view 는 cat 버튼 하나 들어있는 레이아웃
        val view = LayoutInflater.from(context).inflate(R.layout.sikdangchoice_cat, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return catArrayListSize
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        //리사이클러뷰 성능 위해 재사용하기 떄문에 재사용 하지 못하게 해줘야 한다
        holder.setIsRecyclable(false)
        holder.bind()
    }

    public fun setVPAdapter(sikdangChoiceMenuViewPagerAdapter: SikdangChoiceMenuViewPagerAdapter){
        vpAdapter = sikdangChoiceMenuViewPagerAdapter
    }

    private fun getCount(catName: String?):Int{
        var count = 0
        var i = 0
        while (i<catArrayListSize){
            if (catName== this!!.catArrayList!![i]){
                break
            }
            i++
        }
        count = i
        return count
    }

    public fun scrollPosition(pos: Int){
        sikdangChoiceCatLine.smoothScrollToPosition(pos)//그냥 scrollToPosition 쓰면 늦게 옯겨진다
    }

    public fun getCurruntNum():Int{
        var i = 0
        while (i<catArrayListSize){
            if (toggleArrayList[i]== true){
                break
            }
            i++
        }
        Log.d("확인 getCurruntNum()", "종료")
        return i
    }


    public fun toggleOn(buttonNum:Int){
        if(buttonNum >= catArrayListSize) return
        var sikdangChoice_toggleButton = sikdangChoice_toggleButton_arrayList[buttonNum]
        var catName = catArrayList?.get(buttonNum)



        if (sikdangChoice_toggleButton.isChecked){//꺼진 버튼 누를 시 켜지고 다른 버튼 꺼지도록 한다
            //Log.d("확인 SikdangChoiceCatAdapter   sikdangChoice_toggleButton.setOnCheckedChangeListener ", "isChecked"+selectedCat+catName)
            var i = 0
            var catCount = getCount(catName)
            while (i< sikdangChoice_toggleButton_arrayList.size){//와일문 전체 돈다
                //Log.d("확인 SikdangChoiceCatAdapter   sikdangChoice_toggleButton.setOnCheckedChangeListener ", "와일문 돔"+i.toString() +"/"+sikdangChoice_toggleButton_arrayList.size.toString())
                if(i != catCount){
                    toggleArrayList[i] = false//불리스트 수정
                    //Log.d("확인 SikdangChoiceCatAdapter   sikdangChoice_toggleButton.setOnCheckedChangeListener ", i.toString()+" "+catArrayList?.get(i)+" "+"토글 오프")
                    if (lastBindCount>=i)sikdangChoice_toggleButton_arrayList[i].isChecked=false//토글 끔 if 는 바인드된 버튼 위치가 앞쪽이면 뒤쪽의 토글버튼은 건드리지 않는다
                }
                else if (i == catCount){
                    //Log.d("확인 sikdangChoice_toggleButton.setOnClickListener", catArrayList?.get(i).toString() + "토글 온")
                    toggleArrayList[i] = true//불리스트 수정
                    if (lastBindCount>=i)sikdangChoice_toggleButton_arrayList[i].isChecked=true//토글 킴 if 는 바인드된 버튼 위치가 앞쪽이면 뒤쪽의 토글버튼은 건드리지 않는다


                }
                i++
            }
            //Log.d("확인 sikdangChoice_toggleButton.setOnClickListener", "와일문 끝")

        }else{//이미 켜진 버튼 눌러도 변화 없도록 함
            sikdangChoice_toggleButton.setChecked(true)
        }
        var i = 0
        while( i <=lastBindCount){
            if (i==buttonNum){
                sikdangChoice_toggleButton_arrayList[i].setChecked(true)
            }
            else{
                sikdangChoice_toggleButton_arrayList[i].setChecked(false)
            }
            i++
        }


    }

    //맨 처음 선택된 카테고리만 킴



    inner class Holder (itemView: View?) : RecyclerView.ViewHolder(itemView!!){


        fun bind(){
            var sikdangChoice_toggleButton : ToggleButton = itemView.findViewById(R.id.sikdangchice_toggleButton)
            var catName: String? = catArrayList?.get(position)//이번에 바인드하는 카테고리명
            var catCount = getCount(catName)
            if (lastBindCount < catCount) lastBindCount = catCount//바인드되어있는 최대 숫자 커지면 수정함
            sikdangChoice_toggleButton_arrayList[catCount] = (sikdangChoice_toggleButton)//토글버튼리스트에 바인드된 토글버튼 넣음



            //Log.d("종료지점확인 SikdangChoiceCatAdapter", "holder.bind()"+selectedCat+catName)

            if (isFirst == true){
                scrollPosition(firstCatCount)
                toggleArrayList[firstCatCount] = true
                //Log.d("확인 sikdangChoiceCatAdapter.Holder.bind", firstCatCount.toString())
            }
            //처음 바인드 될 때 클릭해서 들어온 버튼의 카테고리 킴
            if ((catName == selectedCat)&& (isFirst==true)){
                sikdangChoice_toggleButton.setChecked(true)

                isFirst=false
            }

            //토글버튼 바인드
            sikdangChoice_toggleButton.text=catName
            sikdangChoice_toggleButton.textOn=catName
            sikdangChoice_toggleButton.textOff=catName

            //토글리스트에 버튼이 켜져있는상태인지 확인하고 켜져있는 상태이면 버튼 킴, 아니면 버튼 끔
            if (toggleArrayList[catCount] == true){
                sikdangChoice_toggleButton.setChecked(true)
            }
            else{
                sikdangChoice_toggleButton.setChecked(false)
            }
            //토글버튼을 ArrayList에 추가해 이 클래스 밖(SikdangChoice 클래스)에세 토글버튼을 사용할 수 있게 한다


            //토글 버튼 클릭했을 때

            sikdangChoice_toggleButton.setOnClickListener {
                //Log.d("확인 sikdangChoice_toggleButton.setOnClickListener", "클릭")
                if (sikdangChoice_toggleButton.isChecked){//꺼진 버튼 누를 시 켜지고 다른 버튼 꺼지도록 한다
                    //Log.d("확인 SikdangChoiceCatAdapter   sikdangChoice_toggleButton.setOnCheckedChangeListener ", "isChecked"+selectedCat+catName)
                    var i = 0
                    var catCount = getCount(catName)
                    while (i< sikdangChoice_toggleButton_arrayList.size){//와일문 전체 돌며 불리스트와 버튼리스트 키고 끈다
                        //Log.d("확인 SikdangChoiceCatAdapter   sikdangChoice_toggleButton.setOnCheckedChangeListener ", "와일문 돔"+i.toString() +"/"+sikdangChoice_toggleButton_arrayList.size.toString())
                        if(i != catCount){
                            toggleArrayList[i] = false//불리스트 수정
                            //Log.d("확인 SikdangChoiceCatAdapter   sikdangChoice_toggleButton.setOnCheckedChangeListener ", i.toString()+" "+catArrayList?.get(i)+" "+"토글 오프")
                            if (lastBindCount>=i)sikdangChoice_toggleButton_arrayList[i].isChecked=false//토글 끔 if 는 바인드된 버튼 위치가 앞쪽이면 뒤쪽의 토글버튼은 건드리지 않는다
                        }
                        else if (i == catCount){
                            //Log.d("확인 sikdangChoice_toggleButton.setOnClickListener", catArrayList?.get(i).toString() + "토글 온")
                            toggleArrayList[i] = true//불리스트 수정
                            if (lastBindCount>=i)sikdangChoice_toggleButton_arrayList[i].isChecked=true//토글 킴 if 는 바인드된 버튼 위치가 앞쪽이면 뒤쪽의 토글버튼은 건드리지 않는다
                            if (isFirst == false) {
                                vpAdapter.setPagerPos(i)
                            }
                        }
                        i++
                    }
                    //Log.d("확인 sikdangChoice_toggleButton.setOnClickListener", "와일문 끝")

                }else{//이미 켜진 버튼 눌러도 변화 없도록 함
                    sikdangChoice_toggleButton.setChecked(true)
                }

            }



        }

    }

}