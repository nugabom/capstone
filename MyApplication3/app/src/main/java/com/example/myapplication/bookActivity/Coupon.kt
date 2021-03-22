package com.example.myapplication.bookActivity

class Coupon(
    val coupon_id: String,
    val min_price: Int,
    val discount: String,
    val type : Int,
    var used : Boolean
)
{
}

data class _coupon (
    val coupon_id : String? = null,
    val discount : String? = null,
    val min_price : Int? = null,
    val coupon_exp : String? = null,
    val type : Int? = null,
    val expire : String? = null
)