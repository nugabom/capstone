package com.example.myapplication.bookMenu

import java.io.Serializable

class DataMenuToPay:Serializable {
    var tablePNum = ArrayList<ArrayList<Int>>()
    var tableMenuNum = ArrayList<ArrayList<Int>>()
    var sikdangId = ""
    var sikdangName = ""

    public fun setAL(tablePNum_:ArrayList<ArrayList<Int>>, tableMenuNum_:ArrayList<ArrayList<Int>>){
        tablePNum=tablePNum_
        tableMenuNum=tableMenuNum_
    }
    public fun setOnSikdangInfo(sikdangId_:String, sikdangName_:String){
        sikdangId=sikdangId_
        sikdangName=sikdangName_
    }

}