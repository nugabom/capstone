package com.example.myapplication.mypage

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.example.myapplication.bookActivity.Coupon

class ShowCouponDialog (
    context: Context,
    var coupons : ArrayList<Coupon>
) : Dialog(context)
{
}