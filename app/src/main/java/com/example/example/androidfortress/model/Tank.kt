package com.example.example.androidfortress.model

import android.graphics.Color

open class Tank {

    open var x = ((GameConfig.SCREEN_WIDTH/2).toInt()..GameConfig.SCREEN_WIDTH.toInt()).random().toFloat()
    var y = 0f
    open var color = Color.RED

    open fun setTank(landForm: LandForm) {

//        var x1 = 0f
//        var x2 = 0f
//        var y1 = 0f
//        var y2 = 0f
//
//        for (i in vertex.indices) {
//            if (vertex[i][0] > x) {
//                x1 = vertex[i - 1][0]
//                y1 = vertex[i - 1][1]
//                x2 = vertex[i][0]
//                y2 = vertex[i][1]
//                break
//            }
//        }
//
//        // 두 점을 지나는 직선의 방정식
//        y = (y2 - y1) / (x2 - x1) * (x - x1) + y1

        y = landForm.isOnLandForm(x)

    }
}