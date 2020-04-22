package com.yinlei.sunnyweather.logic

import androidx.lifecycle.liveData
import com.yinlei.sunnyweather.logic.model.Place
import com.yinlei.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

// 仓库层的统一封装入口
object Repository {

    // 将异步获取数据以响应式编程的方式通知给上一层，通常返回liveData对象
    // liveData()函数是lifecycle-livedata-ktx库的：自动构建并返回一个livedata对象，代码块中提供了一个挂起函数上下文。
    // Dispatchers.IO意思是在子线程中运行
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)// 通知数据的变化。因为无法直接返回Livedata对象，所以这里提供了替代方法
    }

}