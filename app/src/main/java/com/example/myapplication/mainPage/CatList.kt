package com.example.myapplication.mainPage
//카테고리 리스트 저장하는 함수
class CatList {
    //여기서 수정하면 카테고리 리스트 변함
    //2의 배수로 넣어야 함 아니면 끝에 하나 짤림
    private val catArray = arrayListOf("소고기", "돼지고기", "닭고기", "한식",
            "중식", "일식", "양식", "면",
            "분식", "포차", "디저트", "프랜차이즈",
            "뭐가", "문제야", "대체", "뭘",
            "잘못한건데", "제발좀", "돼라", "쫌",
            "어떤게", "문제인지", "알아봐야", "하는데")

    public fun getCatArray(): ArrayList<String> {
        return catArray
    }

}