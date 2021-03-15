package com.example.myapplication.bookTable

import android.util.Log
import java.io.Serializable

//이것도 DB접속해서 가져와야하나?
//TableFloorFragment에서 초기화

//TableFragment에서 초기화되어 TableVPAdapter에 매개변수로서 전달 -> TableFloorFragment로 serializable - bundle로 전달

class TableData(val sikdangId:Int, val bookTime:String):Serializable {
    //가로좌표 세로좌표 가로길이 세로길이

    var tableList = ArrayList<Table>()//각 테이블 정보 담긴 리스트
    var floorList = ArrayList<Int>()//식당 각 몇층인지
    var tableNumList = ArrayList<Int>()//각 층에 테이블 몇개인지
    var accumTableNumList = ArrayList<Int>()//테이블 개수 축적



    init{
        setData()
    }
    //여기서 DB 접근 현재는 임시데이터
    //tableList 만 채워주면 된다
    private fun setData(){

        floorList.add(1)
        floorList.add(3)

        tableList.add(Table(0.5F, 0.3F, 50, 50, 2, 1, true, true))
        tableList.add(Table(0.6F, 0.3F, 50, 50, 2, 1, false, true))
        tableList.add(Table(0.7F, 0.3F, 30, 30, 4, 1, false, true))
        tableList.add(Table(0.2F, 0.6F, 60, 30, 6, 1, false, false))
        tableList.add(Table(0.35F, 0.6F, 60, 30, 3, 1, true, false))

        tableList.add(Table(0.3F, 0.3F, 50, 50, 2, 3, true, true))
        tableList.add(Table(0.3F, 0.4F, 50, 50, 2, 3, false, true))
        tableList.add(Table(0.5F, 0.4F, 30, 30, 4, 3, false, true))
        tableList.add(Table(0.2F, 0.6F, 60, 30, 6, 3, false, false))
        tableList.add(Table(0.35F, 0.6F, 60, 30, 3, 3, true, false))
        tableList.add(Table(0.35F, 0.75F, 60, 30, 3, 3, false, false))
        setFloorTable()

    }
    //층별 테이블 수 계산
    private fun setFloorTable(){
        var i = 0
        var table = 0
        var floor = floorList[0]
        while(i < tableList.size){
            if (tableList[i].floor > floor){
                tableNumList.add(table)
                table = 0
                floor = tableList[i].floor
            }
            table ++
            i++
        }
        tableNumList.add(table)

        i = 0
        accumTableNumList.add(tableNumList[0])
        //Log.d("확인 tableAccum", accumTableNumList[i].toString())
        while(i < tableNumList.size-1){
            accumTableNumList.add(accumTableNumList[i]+tableNumList[i+1])
            i++
            //Log.d("확인 tableAccum", accumTableNumList[i].toString())
        }

    }


    //locX 테이블의 가로 좌표
    //locY 테이블의 세로 좌표
    //lengX 테이블의 가로길이
    //lengY 테이블의 세로길이
    //maxP 이 테이블에 최대 몇명 예약 가능한지
    //floor 테이블이 위치한 층
    //isbooked 테이블이 예약되어있는가
    //isCircle 테이블이 원형인가
    inner class Table(var locX:Float, var locy:Float, var lengX:Int, var lengY:Int, var maxP:Int, var floor:Int, var isBooked:Boolean, var isCircle:Boolean):Serializable{
    }




}