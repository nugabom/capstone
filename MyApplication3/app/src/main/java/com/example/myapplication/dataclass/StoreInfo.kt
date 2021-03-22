package com.example.myapplication.dataclass

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class StoreInfo(
        var store_id : String?="",
        var store_name : String? = ""
) : Serializable
