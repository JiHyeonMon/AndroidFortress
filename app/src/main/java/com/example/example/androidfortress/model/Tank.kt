package com.example.example.androidfortress.model

import android.graphics.Color

open class Tank {

    open var x = ((GameConfig.SCREEN_WIDTH/2).toInt()..GameConfig.SCREEN_WIDTH.toInt()).random().toFloat()
    var y = 0f
    open var color = Color.RED

    open fun setTank(mountain: Mountain) {

        y = mountain.getY(x)

    }
}