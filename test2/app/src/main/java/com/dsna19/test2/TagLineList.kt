package com.dsna19.test2

import android.app.Activity
import android.os.Bundle

class TagLineList(){

    var tagList:ArrayList<TagLine> = arrayListOf(
            TagLine("소고기", "닭고기", "돼지고기"),
            TagLine("매운맛", "달콤한 맛", "구수한맛"),
            TagLine("짠맛", "뜨거운것", "시원한 것"),
            TagLine("생선", "새우", "조개"),
            TagLine("소주", "맥주", "막걸리"),
            TagLine("데이트", "단체", "혼밥")
    )
    fun getTagLineList(): ArrayList<TagLine>{
        return tagList;
    }


}