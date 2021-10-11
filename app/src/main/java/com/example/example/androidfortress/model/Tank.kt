package com.example.example.androidfortress.model

import kotlin.properties.Delegates

class Tank {

    var x by Delegates.notNull<Int>()
    var y by Delegates.notNull<Int>()

    fun setTank(vertex: MutableList<ArrayList<Int>>) {
        x = (0..GameConfig.SCREEN_WIDTH).random()

        var x1 = 0
        var x2 = 0
        var y1 = 0
        var y2 = 0

        for (i in vertex.indices) {
            if (vertex[i][0] > x) {
                x1 = vertex[i - 1][0]
                y1 = vertex[i - 1][1]
                x2 = vertex[i][0]
                y2 = vertex[i][1]
                break
            }
        }

        // 두 점을 지나는 직선의 방정식
        y = (y2 - y1) / (x2 - x1) * (x - x1) + y1
    }
}