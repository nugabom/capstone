package com.example.myapplication.bookTable

import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.bookTime.BookData
import com.example.myapplication.bookTime.BookTime

//tableVPAdapter에서 사용
//테이블 레이아웃 부분 바인드
class TableFloorFragment():Fragment()  {
    //NoticeDialogFragment.NoticeDialogListener
    //lateinit var bookData:BookData
    var pos:Int = 0//몇 번째 페이지인가
    var tfFragment = this
    var tableButtonAR = ArrayList<Button>()//현재 층의 테이블의 버튼 리스트
    var numAL =ArrayList<Int>()//각 테이블에 몇명 예약 넣었는가
    lateinit var tableData:TableData
    var floorNum : Int = 0//현재 층

    //var bookTimeActivity= activity as BookTime

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("확인 TableFloorFragment()", "onCreateView")

        var view = inflater.inflate(R.layout.booktable_floorfragment, container, false)



        bind(view)
        return view
    }


    public fun bind(view:View){
        lateinit var bookData:BookData
        var bundle = getArguments()
        if (bundle != null) {
            bookData = bundle.getSerializable("bookData") as BookData
            tableData = bundle.getSerializable("tableData") as TableData
            pos = bundle.getInt("pos")
        }
        else{
            Log.d("확인 TimeFragment.bind", "else")
        }
        var floorText:TextView = view.findViewById(R.id.floorText)
        //tableData= TableData(bookData.getSikdangId(), bookData.getBookTime())
        floorNum = tableData.floorList[pos]
        floorText.setText(floorNum.toString()+"층 "+bookData.getBookTime())

        /*
        var tempString = ""
        var ii = 0
        while (ii < tableData.tableBookArrayList.size){
            var jj = 0
            while (jj < tableData.tableBookArrayList[ii].size){
                tempString+=tableData.tableBookArrayList[ii][jj]
                jj++
            }
            ii++
        }
        Log.d("확인 TableFloorFragment() 테이블 정보 받은것 확인", tableData.tableBookArrayList.size.toString()+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        Log.d("확인 TableFloorFragment() 테이블 정보 받은것 확인", tempString)*/





        var tableLayout:ConstraintLayout = view.findViewById(R.id.floorLayout)

        //var layoutParam = RelativeLayout.LayoutParams (ViewGroup.LayoutParams. WRAP_CONTENT , ViewGroup.LayoutParams. WRAP_CONTENT )


        //빨강 CC5555
        //초록 55CC55
        //회색 CCCCCC

        var i = 0



        i=0//테이블 통합
        //버튼별 세팅
        while(i<tableData.tableList.size){
            var count = i//테이블리스트의 몇번째인가
            var button= Button(getContext())
            //tableData.tableBookArrayList[pos][count] 는 그 테이블에 현재 내가선택한 인원수
            if(tableData.tableList[count].floor == floorNum) {
                //val roundDrawable = resources.getDrawable(R.drawable.button_round, null)
                //button.background = roundDrawable
                var buttonNum = count
                if(pos>0){
                    buttonNum = count - tableData.accumTableNumList[pos-1]
                }
                if (tableData.tableList[count].isCircle == true)//원형테이블
                {

                    var roundDrawable = resources.getDrawable(R.drawable.button_round_gray, null)
                    if (tableData.tableList[count].isBooked == true) {//예약이 돼있으면
                        roundDrawable = resources.getDrawable(R.drawable.button_round_red, null)
                        button.setOnClickListener {
                            val myToast = Toast.makeText(context, "테이블이 이미 예약되어있습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }else {//예약 안되어있으면
                        roundDrawable = resources.getDrawable(R.drawable.button_round_gray, null)
                        button.setOnClickListener {

                            //Log.d("확인 원형테이블 인원", tableData.circleTableList[i].maxP.toString())
                            //Log.d("확인 넣은 변수 확인@@@@@@@@@@@@@@@@@@@@@@@@@@@", count.toString()+" "+numAR.size.toString())
                            //Log.d("확인 넣은 변수 확인@@@@@@@@@@@@@@@@@@@@@@@@@@@", numAR[buttonNum].toString()+" "+buttonNum.toString())
                            showDialog(tableData.tableList[count].maxP, count, numAL[buttonNum])
                            //showDialog(tableData.tableList[count].maxP, count, tableData.tableBookArrayList[pos][count])
                        }
                    }
                    button.background = roundDrawable
                } else {//사각 테이블
                    if (tableData.tableList[count].isBooked == true) {//이미 예약 된 경우
                        button.setBackgroundColor(parseColor("#CC5555"))
                        button.setOnClickListener {
                            val myToast = Toast.makeText(context, "테이블이 이미 예약되어있습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {//dialogFragment 검색 or kotlin popup
                        button.setBackgroundColor(parseColor("#CCCCCC"))
                        button.setOnClickListener {
                            //Log.d("확인 넣은 변수 확인@@@@@@@@@@@@@@@@@@@@@@@@@@@", count.toString()+" "+numAR.size.toString())
                            //Log.d("확인 넣은 변수 확인@@@@@@@@@@@@@@@@@@@@@@@@@@", numAR[buttonNum].toString()+" "+buttonNum.toString())
                            showDialog(tableData.tableList[count].maxP, count, numAL[buttonNum])
                            //showDialog(tableData.tableList[count].maxP, count, tableData.tableBookArrayList[pos][count])
                        }
                    }


                }


                var layoutParams = ConstraintLayout.LayoutParams(dpToPx(tableData.tableList[i].lengX), dpToPx(tableData.tableList[i].lengY))
                //with와 height에 픽셀값 들어감 => dp를 픽셀값으로 변환한 값 들어가야 한다.

                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                layoutParams.horizontalBias = tableData.tableList[count].locX//0.5가 중앙
                layoutParams.verticalBias = tableData.tableList[count].locy

                button.setLayoutParams(layoutParams)
                tableLayout.addView(button)
                numAL.add(0)
                tableButtonAR.add(button)
            }

            i++
        }




    }

    public fun dpToPx(dp:Int):Int {
        var density:Float = getResources().getDisplayMetrics().density;
        return Math.round(dp.toFloat() * density.toFloat()).toInt()
    }

    public fun showDialog(tablePerson:Int, tableNum: Int, curTableP:Int){
        var customDialog = BookPersonDialog(this!!.getContext()!!, tablePerson, tfFragment, tableNum, floorNum, curTableP)
        customDialog!!.show()
    }
    //버튼 클릭의 호출 순서 : PersonNumRVAdapter -> BookPersonDialog->TableFloorFragment(여기)
    //dialog에서 인원수 선택 버튼 클릭될 경우
    public fun pNumButtonClicked(tableNum:Int, pnum:Int, floor:Int){
        //Log.d("확인 floor", floor.toString()+floorNum.toString()+tableNum.toString())
        //Log.d("확인 floor Pos : ", pos.toString())
        var floorTable = tableNum
        if(pos>0){
            floorTable = tableNum - tableData.accumTableNumList[pos-1]
        }
        if (floor == floorNum) {//Log.d("확인 TableFloorFragment()", "dialog 예약 확인"+tableNum.toString()+" "+tableButtonAR.size.toString())
            //Log.d("확인 floor", "종료지점 확인 0 "+tableData.tableList.size+floorTable.toString())
            if (tableData.tableList[tableNum].isCircle == true) {//원형의 경우
                //Log.d("확인 floor", "종료지점 확인 1")
                var roundDrawable = resources.getDrawable(R.drawable.button_round_green, null)
                tableButtonAR[floorTable].background = roundDrawable
                //Log.d("확인 floor", "종료지점 확인 2")
            } else {
                //Log.d("확인 floor", "종료지점 확인 1.2")
                tableButtonAR[floorTable].setBackgroundColor(parseColor("#55CC55"))
                //Log.d("확인 floor", "종료지점 확인 2.2")
            }
            //Log.d("확인 floor", "종료지점 확인 3")
            tableButtonAR[floorTable].setText(pnum.toString())
            //Log.d("확인 floor", "종료지점 확인 4")
            numAL[floorTable] = pnum
            //Log.d("확인 floor", "종료지점 확인 5")
            //logNumAR()
            transferTableData()
        }
        //Log.d("확인 floor", "종료지점 확인 6")

    }
    public fun cancelButtonClicked(tableNum:Int, floor:Int){
        var floorTable = tableNum
        if(pos>0){
            floorTable = tableNum - tableData.accumTableNumList[pos-1]
        }

        if (tableData.tableList[tableNum].isCircle==true){//원형의 경우
            var roundDrawable= resources.getDrawable(R.drawable.button_round_gray, null)
            tableButtonAR[floorTable].background = roundDrawable
        }
        else tableButtonAR[floorTable].setBackgroundColor(parseColor("#CCCCCC"))
        tableButtonAR[floorTable].setText("")
        numAL[floorTable] = 0
        transferTableData()
        //logNumAR()
    }
    public fun logNumAR(){
        var i = 0
        var aa=""
        while (i<numAL.size){
            aa+=numAL[i].toString()
            i++
        }
        //Log.d("확인 numAR", aa)
    }

    //초기 테이블 데이터를 액티비티의 변수로 전달/초기화
    /*
    private fun tableDataInitInActivity(){
        //var bookTimeActivity= activity as BookTime
        bookTimeActivity.tableInfoInit()
    }*/
    //데이터 변동시마다 액티비티에 값을 전달
    private fun transferTableData(){
        var bookTimeActivity= activity as BookTime
        bookTimeActivity.setTableInfo(pos, numAL)
    }










}