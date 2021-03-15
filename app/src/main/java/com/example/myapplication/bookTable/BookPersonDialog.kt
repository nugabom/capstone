package com.example.myapplication.bookTable

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.bookTime.BookTime
//TableFloorFragmet에서 사용
//다이얼로그 설정
//pNum 테이블 최대 인원수, tableFloorFragment 이 다이럴로그 호출한 프래그먼트, tableNum 이 테이블이 테이블 목록의 몇 번째 테이블인지 floor 몇층인지 curP 현재 이 테이블에 몇명 세팅되어있는지
class BookPersonDialog(context:Context, val pNum:Int, val tableFloorFragment: TableFloorFragment, val tableNum: Int, val floor:Int, val curP:Int): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.booktable_person_num_dialog)
        var bookPersonDialog =this
        var personNumRVAdapte = PersonNumRVAdapter(context, pNum, bookPersonDialog, floor, curP)
        var personNumRV:RecyclerView = findViewById(R.id.personNumRV)
        personNumRV.adapter = personNumRVAdapte

        var personNumLM = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)//인원 버튼
        personNumRV.layoutManager=personNumLM
        personNumRV.setHasFixedSize(true)

        var cancelButton = findViewById<Button>(R.id.cancelButton)//취소버튼
        cancelButton.setOnClickListener {
            cancelButtonClicked()
        }
        var dialogCloseButton = findViewById<Button>(R.id.dialogCloseButton)//닫기버튼
        dialogCloseButton.setOnClickListener {
            this.dismiss()
        }
        var menuChoiceButton = findViewById<Button>(R.id.menuChoiceButton)
        menuChoiceButton.setOnClickListener { //메뉴 선택 버튼

        }
    }
    fun buttonClicked(pNum:Int){
        tableFloorFragment.pNumButtonClicked(tableNum, pNum, floor)
        //this.dismiss()
    }
    fun cancelButtonClicked(){
        tableFloorFragment.cancelButtonClicked(tableNum, floor)
        this.dismiss()
    }



}