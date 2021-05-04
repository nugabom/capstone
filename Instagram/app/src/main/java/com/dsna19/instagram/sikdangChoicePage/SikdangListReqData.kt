package com.example.myapplication.sikdangChoicePage

import com.google.firebase.database.FirebaseDatabase

//식당 위치 관련 클래스
//SikdangMenuData 에 데이터 전하는 용도의 클래스
//통째로 SikdangMenuData 객체로 넘긴다
//SikdsngChoice 에서 생성되어 maxDist 넣어줌
//SikdangChoiceMenuViewPagerAdapter 클래스에서 cat 채우고 ->  SikdangChoiceMenuFragment -> SikdangChoiceMenuAdapter 에서 pos 채우고
//마지막으로 SikdangMenuData 클래스 생성하면서 넘긴다.
//CoorXT는 언제넣을지 아직 미정
//여기에 식당 목록 몇개인지도 넣어서 중간에 다른 객체로 넘길 용도로 추가하는것도 괜찮을듯
/*
data class SikdangListReqData(
    val Lat : Double? = null,
    val Lng : Double? = null,
    val id : String? = null,
    val name : String? = null
)

 */
class SikdangListReqData() {
    private var maxDist:Int? = 0
    private var cat:String? = ""
    private var pos:Int? = 0
    private var coorX:Float? = 0.toFloat()
    private var coorY:Float? = 0.toFloat()


    public fun getMaxDist():Int{
        return maxDist!!
    }
    public fun getCat():String{
        return cat!!
    }
    public fun getPos():Int{
        return pos!!
    }
    public fun getCoorX():Float{
        return coorX!!
    }
    public fun getCoorY():Float{
        return coorY!!
    }

    public fun setMaxDist(dist_:Int){maxDist=dist_}
    public fun setCat(cat_:String){cat=cat_}
    public fun setPos(pos_:Int){pos=pos_}
    public fun setCoorXY(x:Float, y:Float){
        coorX=x
        coorY=y
    }
}