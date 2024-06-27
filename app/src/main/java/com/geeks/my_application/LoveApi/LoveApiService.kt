package com.geeks.my_application.LoveApi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LoveApiService {
    @GET("getPercentage")
    fun getPercentage(
        @Header("x-rapidapi-Key")key:String,
        @Header("x-rapidapi-Host")host:String,
        @Query("fname")firstName:String,
        @Query("sname")secondName:String
    ): Call<LoveResult>
}