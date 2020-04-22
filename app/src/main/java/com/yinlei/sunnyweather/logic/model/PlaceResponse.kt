package com.yinlei.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

// 搜索城市数据的Model
data class PlaceResponse(val status: String, val places: List<Place>)

// JSON中一些字段命名可能和Kotlin规范不一致，所以使用@SerializedName来让JSON字段和Kotlin字段之间建立映射关系
data class Place(val name: String, val location: Location, @SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)