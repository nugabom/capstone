package com.example.myapplication.payPage

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bookTable.TableFloorFragment
//쿠폰 정보 띄우는 다이얼로그
class PayPageCouponDialog(context: Context, val couponData:CouponData, val sikdangId:String): Dialog(context) {
    public var couponMatchList = ArrayList<Boolean>()//각 쿠폰이 이 식당에 사용되는 쿠폰인지 확인, 어댑터에서 확인한다
    public var couponMatchNumList = ArrayList<Int>()//매치되는 쿠폰이 각각 몇번째인지 저장하는 ArrayList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paypage_coupondialog)
        val payPageCouponDialog = this

        initCouponMatchList()


        var couponListRV:RecyclerView = findViewById(R.id.couponListRV)
        var couponListRVAdapter = CouponListRVAdapter(context, couponData, payPageCouponDialog)
        couponListRV.adapter = couponListRVAdapter

        var couponListLM = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)//인원 버튼
        couponListRV.layoutManager=couponListLM
        couponListRV.setHasFixedSize(true)



    }

    public fun initCouponMatchList(){
        var i = 0
        //Log.d("확인 다이얼로그 쿠폰 개수 확인", couponData.couponList.size.toString())
        while (i<couponData.couponList.size){
            //Log.d("확인 다이얼로그 쿠폰 할당", i.toString()+"/"+couponData.couponList.size+" "+couponData.couponList[i].sikdangId)
            if (couponData.couponList[i].sikdangId == sikdangId){
                couponMatchList.add(true)
                couponMatchNumList.add(i)
            }
            else{
                couponMatchList.add(false)
            }
            i++
        }
    }

}