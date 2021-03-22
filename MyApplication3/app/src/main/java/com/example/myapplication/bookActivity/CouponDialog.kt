package com.example.myapplication.bookActivity

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CouponDialog
    (
        context: Context,
        val coupons : ArrayList<Int>,
        val listener : PayActivity
    ) : Dialog(context)
{
    lateinit var recyclerView: RecyclerView
    lateinit var complete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paypage_coupondialog)

        recyclerView = findViewById(R.id.couponListRV)
        complete = findViewById(R.id.couponSelectButton)
        recyclerView.adapter
    }
}