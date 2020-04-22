package com.yinlei.sunnyweather.logic.network

import com.yinlei.sunnyweather.SunnyWeatherApplication
import com.yinlei.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 访问彩云天气城市搜素API
interface PlaceService {

    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}