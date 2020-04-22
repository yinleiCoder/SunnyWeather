package com.yinlei.sunnyweather.logic

import androidx.lifecycle.liveData
import com.yinlei.sunnyweather.logic.dao.PlaceDao
import com.yinlei.sunnyweather.logic.model.Place
import com.yinlei.sunnyweather.logic.model.Weather
import com.yinlei.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

// 仓库层的统一封装入口
object Repository {

    // 将异步获取数据以响应式编程的方式通知给上一层，通常返回liveData对象
    // liveData()函数是lifecycle-livedata-ktx库的：自动构建并返回一个livedata对象，代码块中提供了一个挂起函数上下文。
    // Dispatchers.IO意思是在子线程中运行
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
//        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
//                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
//        } catch (e: Exception) {
//            Result.failure<List<Place>>(e)
//        }
//        emit(result)// 通知数据的变化。因为无法直接返回Livedata对象，所以这里提供了替代方法
    }

    // 显示天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
//        val result = try {
            coroutineScope {// async 需要在协程作用域内才能调用
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                } else {
//                    Result.failure<List<Weather>>(RuntimeException("realtime response status is ${realtimeResponse.status} daily response status is ${dailyResponse.status}"))
                    Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status} daily response status is ${dailyResponse.status}"))
                }
            }
//        } catch (e: Exception) {
//            Result.failure<List<Weather>>(e)
//        }
//        emit(result)
    }

    // 可以在某个统一的入口函数中进行封装，使得只需要进行依次try catch处理就行了
    // 从而避免了在仓库层中为每个网络请求都进行try catch 处理,减少了复杂度.
    // liveData()是拥有挂起函数上下文的。
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) = liveData<Result<T>>(context) {
        val result = try {
            block()
        } catch (e: Exception){
            Result.failure<T>(e)
        }
        emit(result) // 将执行结果发送出去
    }

    // 记录选中的城市
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

}