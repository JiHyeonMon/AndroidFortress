package com.example.example.androidfortress.model

import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

class Missile {

    private val G = GameConfig.G
    var x by Delegates.notNull<Double>()
    var y by Delegates.notNull<Double>()
    var V by Delegates.notNull<Int>()
    var angle by Delegates.notNull<Double>()

    var t = 0.0

    fun initMissile(x: Float, y: Float) {
        this.x = x.toDouble()
        this.y = y.toDouble()
        this.V = 10
        this.angle = 45.0
    }

    fun move() {
        t += 0.05

        val dx = V * cos(Math.toRadians(angle)) * t
        val dy = V * sin(Math.toRadians(angle)) * t - G * t * t / 2

        x += dx
        y -= dy

    }

    fun changeAngle(da: Int) {
        this.angle += da.toDouble()
    }

    fun changeSpeed(dv: Int) {
        this.V += dv
    }
}