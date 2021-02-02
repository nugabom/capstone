package com.dsna19.firebase

import java.io.Serializable

data class ChatData(val nickname: String, val msg: String){
    constructor() : this("", "")
}