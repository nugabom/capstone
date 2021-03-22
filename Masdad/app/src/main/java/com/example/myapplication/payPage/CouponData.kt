package com.example.myapplication.payPage

class CouponData(val userId:Int) {
    var couponList = ArrayList<Coupon>()

    init{
        setData()
    }

    private fun setData(){
        val coupon1 = Coupon(12345678, 10000, 1, 78965412350, 210912, "쿠폰1")
        val coupon2 = Coupon(12345678, 0, 1, 78965412351, 211212, "쿠폰2")
        val coupon3 = Coupon(12345678, 50000, 1, 78965422350, 220101, "쿠폰3")
        val coupon4 = Coupon(12345679, 10000, 2, 78765412350, 210912, "쿠폰4")
        val coupon5 = Coupon(12345610, 0, 2, 12365412350, 210912, "쿠폰5")
        val coupon6 = Coupon(12345678, 0, 2, 78456412350, 210912, "쿠폰6")
        val coupon7 = Coupon(12345678, 100000, 2, 78965402550, 210912, "쿠폰7")
        val coupon8 = Coupon(12345678, 10000, 3, 78965412582, 210912, "쿠폰8")
        couponList.add(coupon1)
        couponList.add(coupon2)
        couponList.add(coupon3)
        couponList.add(coupon4)
        couponList.add(coupon5)
        couponList.add(coupon6)
        couponList.add(coupon7)
        couponList.add(coupon8)

    }

    //minPrice 쿠폰 사용 위한 최소금액
    //overlap 쿠폰중복사용 여부 1중복 가능 : 1끼리만 중복 가능 2 중복 불가 : 1사용 불가 2 하나만 사용 가능 3 : 중복 가능 2랑도 중복 가능 완전 자유
    //couponExp는 쿠폰 설명

   inner class Coupon(val sikdangId:Int, val minPrice:Int, val overlap:Int, val couponId:Long, val couponExp:Int, val explanation:String)
}