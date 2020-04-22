package com.yinlei.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * {
 *     "status": "ok",
 *     "result": {
 *          "daily": {
 *              "temperature": [{"max": 25.7,"min":20.3},.....],
 *              "skycon": [{"value: "CLOUDY","date":"2020-4-22xxx"},....],
 *              "life_index": {
 *                  "coldRisk": [{"desc": "易发"},...],
 *                  "carWashing": [{"desc": "适宜"},...],
 *                  "ultraviolet": [{"desc": "无"},...],
 *                  "dressing": [{"desc": "舒适"},...],
 *              }
 *          }
 *     }
 * }
 */
// 未来几天天气信息的Model
data class DailyResponse(val status: String, val result: Result) {

    data class Result(val daily: Daily)

    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>, @SerializedName("life_index") val lifeIndex: LifeIndex)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>, val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    data class LifeDescription(val desc: String)

}