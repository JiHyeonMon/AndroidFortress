package com.example.example.androidfortress.model

import kotlin.math.cos
import kotlin.math.sin

class Missile {

    var x = 0.0
    var y = 0.0
    var v = 0
    var theta = 0.0

    fun move(t: Float) {
        x = v * cos(theta) * t
        y = v * sin(theta) * t - 9.8 * t * t / 2
    }

    fun setDirection() {

        // 원의 중심이 (a, b)이고 반지름이 r인 원의 방정식은 (x - a)2 + (y - b)2 = r2
        // 처음 미사일 방향 보여줄 때,
        // tank의 중심좌표 x, y 필요 -> lineTo() ->
    }

    fun setSpeed(v: Int) {
        this.v = v
    }

}