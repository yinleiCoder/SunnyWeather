package com.yinlei.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yinlei.sunnyweather.logic.Repository
import com.yinlei.sunnyweather.logic.model.Place

// ViewModel层，是逻辑层和ui层的桥梁
class PlaceViewModel : ViewModel(){

    private val searchLiveData = MutableLiveData<String>()

    // 用于对界面上显示的城市数据进行缓存。
    // 原则上与界面有关的数据都应该放到ViewModel中，可以保证他们在屏幕旋转时数据不丢失。
    val placeList = ArrayList<Place>()

    // switchMap（）来观察这个searchLiveData对象，否则仓库层返回的LiveData大对象将无法进行观察.每当searchLivedata变化，
    // switchMap()对应的转换函数就会执行，
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query) // 发起仓库层的网络请求/本地缓存
    }

    // 没有直接调用仓库层的方法，而是将搜索参数赋值给了searchLiveData对象
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    // ——————————————————记录选中的城市
    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}