package com.yinlei.sunnyweather.logic.model

// 封装实时天气Model和未来天气Model
class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)