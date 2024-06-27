package com.geeks.my_application.LoveApi

import com.google.gson.annotations.SerializedName

class LoveResult(

    @SerializedName("fname")
    val firstName: String,
    @SerializedName("sname")
    val secondName: String,
    @SerializedName("percentage")
    val percentage: String,
    @SerializedName("result")
    val result: String
    )