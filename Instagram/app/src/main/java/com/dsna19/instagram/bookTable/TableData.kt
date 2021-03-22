package com.example.myapplication.bookTable

import android.util.Log
import android.view.ViewDebug
import java.io.Serializable

//이것도 DB접속해서 가져와야하나?
//TableFloorFragment에서 초기화

//TableFragment에서 초기화되어 TableVPAdapter에 매개변수로서 전달 -> TableFloorFragment로 serializable - bundle로 전달

class TableData(val sikdangId:String/*, val bookTime:String*/):Serializable {
    //가로좌표 세로좌표 가로길이 세로길이
    var tableList = ArrayList<Table>()//각 테이블 정보 담긴 리스트
    var floorList = ArrayList<Int>()//식당 각 몇층인지
    var tableNumList = ArrayList<Int>()//각 층에 테이블 몇개인지
    var accumTableNumList = ArrayList<Int>()//테이블 개수 축적
}

//locX 테이블의 가로 좌표
//locY 테이블의 세로 좌표
//lengX 테이블의 가로길이
//lengY 테이블의 세로길이
//maxP 이 테이블에 최대 몇명 예약 가능한지
//floor 테이블이 위치한 층
//isbooked 테이블이 예약되어있는가
//isCircle 테이블이 원형인가

class Table private constructor(
        val locX: Float?,
        val locy: Float?,
        val lengX: Int?,
        val lengY: Int?,
        val maxP: Int?,
        val floor: Int?,
        val isBooked: Boolean?,
        val isCircle: Boolean?
) {

    data class Builder(
            var locX: Float? = null,
            var locy: Float? = null,
            var lengX: Int? = null,
            var lengY: Int? = null,
            var maxP: Int? = null,
            var floor: Int? = null,
            var isBooked: Boolean? = null,
            var isCircle: Boolean? = null
    ) {
        fun locX(locX: Float) = apply { this.locX = locX }
        fun locy(locy: Float) = apply { this.locy = locy }
        fun lengX(lengX: Int) = apply { this.lengX = lengX }
        fun lengY(lengY: Int) = apply { this.lengY = lengY}
        fun maxP(maxP: Int) = apply { this.maxP = maxP }
        fun floor(floor: Int) = apply { this.floor = floor }
        fun isBooked(isBooked: Boolean) = apply { this.isBooked = isBooked }
        fun isCircle(isCircle: Boolean) = apply { this.isCircle = isCircle }
        fun build() = Table(locX, locy, lengX, lengY, maxP, floor, isBooked, isCircle)
    }
}