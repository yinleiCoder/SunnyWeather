package com.yinlei.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yinlei.sunnyweather.logic.Repository
import com.yinlei.sunnyweather.logic.model.Location

// 显示天气信息的ViewModel
class WeatherViewModel : ViewModel(){

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    // 只要改变locationLiveData的值，那监听locationLiveData的weatherLiveData会转换为可被activity观察的livedata
    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

}