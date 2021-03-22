package com.example.myapplication.sikdangChoicePage

import android.util.Log
import com.example.myapplication.R

//카테고리와 거리 정보 보내면 채워서 돌려받는다
//SikdangListReqData 받아서 데이터 채움
//여기서 pos보고 채운다?
//식당 선택시 이 클래스 생성되면서 다음 액티비티로 넘어간다
//SikdangChoiceMenuViewPagerAdapter 에서 생성

//그러니까 정보를 받아 그 자리에 와야 할 식당의 정보를 넘겨주는 것
//여기서 각 식당 정보 할당해서 이 정보를 보고 어댑터에서 식당 정보 띄워준다
//서버에 카테고리와 거리 정보, 몇번째로 채워야 할지 보내고 서버에서 띄워줄 식당 정해서 그 식당의 정보를 순서대로 이 클래스에 채워준다
//pos는 몇번째로 띄워주는지
//이거는 식당마다 서버에서 데이터를 한 덩어리씩 보냄
//아예 데이터 덩어리로 하는게 나을수도 있을듯듯
class SikdangMenuData() {
    private lateinit var sikdangId : String
    private var sikdangImage : Int = R.drawable.foodimage //식당의 이미지
    private var sikdangName : String = "식당이름"
    private var dist : Float = 0.toFloat()
    private var repMenuArrayList:ArrayList<String> = arrayListOf("대표메뉴1", "대표메뉴2", "대표메뉴3", "대표메뉴4")
    private var anyList:ArrayList<Int> = arrayListOf(0, 0, 0)//순서대로 주차여부 쿠폰 미정
    //주차여부 0이면 주차 불가능 1이면무료주차 1이면 유료주차
    //쿠폰 0이면 없음 1이면 있음
    private var pos = 0
    //lateinit private var reqData




    //카테고리 메뉴의 몇번째 위치인지, 현재 위치 주면
    //식당의 id, 식당 이미지, 이름, 대표메뉴, 기타 리스트, 거리 채워준다
    /*
    constructor(cat:String, pos:Int, coorX : Float, coorY:Float):this(){
        setData(cat, this.pos, coorX, coorY)
    }
    constructor(cat:String, coorX : Float, coorY:Float):this(){
        setData(cat, coorX, coorY)
    }*/
    constructor(reqData: SikdangListReqData):this()
    {
        setData(reqData.getCat(), reqData.getPos(), reqData.getCoorX(), reqData.getCoorY(), reqData.getMaxDist())
    }


    public fun getSikdangId():String{
        return sikdangId
    }
    public fun getSikdangImage() : Int{
        return sikdangImage
    }
    public fun getSikdangName():String{
        return sikdangName
    }
    public  fun getSikdangDist():Int{
        return dist.toInt()
    }
    public fun getrepMenuArrayList():ArrayList<String>{
        return repMenuArrayList
    }
    public fun getanyList():ArrayList<Int>{
        return anyList
    }

    public fun setPos(pos:Int){

    }



    //데이터베이스에서 가저와서 각 변수에 넣어야 함
    private fun setData(cat:String, pos:Int, coorX : Float, coorY:Float, dist_:Int){
        sikdangId = ""
        sikdangImage = R.drawable.foodimage //식당의 이미지
        sikdangName = cat+" "+pos.toString() // 식당 이름
        //Log.d("확인 SikdangMenuData", "setData")
        dist = dist_.toFloat()
        repMenuArrayList = arrayListOf("대표메뉴1", "대표메뉴2", "대표메뉴3", "대표메뉴4") // 대표메뉴 네개
        anyList= arrayListOf(0, 0, 0)
    }
    /*
    fun setData(cat:String, coorX : Float, coorY:Float){
        sikdangId = 12345678
        sikdangImage = R.drawable.foodimage //식당의 이미지
        sikdangName = cat+" "+pos.toString() // 식당 이름
        dist = 123.4567.toFloat()
        repMenuArrayList = arrayListOf("대표메뉴1", "대표메뉴2", "대표메뉴3", "대표메뉴4") // 대표메뉴 네개
        anyList= arrayListOf(0, 0, 0)
    }
*/
}

fun getLocationsFromDB(){

}

