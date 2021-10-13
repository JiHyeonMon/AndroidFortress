package com.example.example.androidfortress.model

import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

class Missile {

    val g = 10
    var x by Delegates.notNull<Double>()
    var y by Delegates.notNull<Double>()
    var v by Delegates.notNull<Int>()
    var angle by Delegates.notNull<Double>()

    var centerY = 0F
    var t = 0.0

    fun move() {
        t += 0.05

        val dx = v * cos(Math.toRadians(angle)) * t
        val dy = v * sin(Math.toRadians(angle)) * t - g * t * t / 2

        x += dx
        y -= dy

    }

    fun initMissile(x: Float, y: Float) {
        this.x = x.toDouble()
        this.y = y.toDouble()
        this.centerY = y
    }

    fun changeAngle(da: Int) {
        this.angle += da.toDouble()
    }

    fun changeSpeed(dv: Int) {
        this.v += dv
    }

    fun setDirection() {

        // 원의 중심이 (a, b)이고 반지름이 r인 원의 방정식은 (x - a)2 + (y - b)2 = r2
        // 처음 미사일 방향 보여줄 때,
        // tank의 중심좌표 x, y 필요 -> lineTo() ->
        val dx = 0f
        val dy = 0f
        (dx-x)*(dx-x) + (dy-y)*(dy-y) = 2*2
    }

}