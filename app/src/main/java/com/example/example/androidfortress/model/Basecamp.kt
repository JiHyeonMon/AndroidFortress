package com.example.example.androidfortress.model

import android.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

class Basecamp : Tank() {

    override var x = (0..(GameConfig.SCREEN_WIDTH / 2).toInt()).random().toFloat()
    override var color = Color.BLUE

    lateinit var missile: Missile

    var guideX by Delegates.notNull<Double>()
    var guideY by Delegates.notNull<Double>()

    fun initMissile() {
        missile = Missile()
        missile.initMissile(x, y)
    }

    override fun setTank(mountain: Mountain) {
        x = ((mountain.vertex[1][0].toInt()..mountain.vertex[2][0].toInt())).random().toFloat()
        y = mountain.getY(x)
    }

    fun setGuide() {

        // 원의 중심이 (a, b)이고 반지름이 r인 원의 방정식은 (x - a)2 + (y - b)2 = r2
        // 처음 미사일 방향 보여줄 때,
        // tank의 중심좌표 x, y 필요 -> lineTo() ->
        guideX = x + cos(Math.toRadians(missile.angle)) * GameConfig.MISSILE_GUIDE_RADIUS
        guideY = y - sin(Math.toRadians(missile.angle)) * GameConfig.MISSILE_GUIDE_RADIUS

    }
}