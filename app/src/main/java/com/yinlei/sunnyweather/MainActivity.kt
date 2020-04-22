package com.yinlei.sunnyweather

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isDark = if (isDarkTheme(this)) {
            "当前已切换为深色主题!"
        } else {
            "当前主题非深色主题.切换为深色主题可以让您的眼部更加舒适、减少电量消耗、延长手机续航。如果您需要开启，请确认您当前的手机版本为Android 10,并打开Settings -> Display -> Dark theme进行开启深色主题。"
        }
        Toast.makeText(this, isDark,Toast.LENGTH_LONG ).show()
    }

    // 代码判断当前系统是否是深色主题
    fun isDarkTheme(context: Context): Boolean {
        val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return flag == Configuration.UI_MODE_NIGHT_YES
    }
}
