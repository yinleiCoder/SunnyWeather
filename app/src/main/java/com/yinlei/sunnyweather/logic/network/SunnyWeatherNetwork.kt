package com.yinlei.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// 统一的网络数据源访问入口，对所有网络请求的API进行封装（单例）(协程简化回调地狱)
object SunnyWeatherNetwork {

    private val placeService = ServiceCreator.create(PlaceService::class.java)

    // 发起搜索城市数据请求(立即发送网络请求，当前协程被阻塞，直到数据返回并恢复当前协程的执行，然后将数据再返回上一层)
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

}