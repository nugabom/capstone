package com.dsna19.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MaskApi {
    @Headers("Accept:application/json")
    @GET("/corona19-masks/v1/storesByGeo/json")
    fun getStoresByGeo(
        @Query("lat") lat : Double,
        @Query("lng") lng: Double,
        @Query("m") m : Int
    ) : Call<StoreSaleResult>

}
