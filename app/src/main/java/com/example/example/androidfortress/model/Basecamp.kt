package com.example.example.androidfortress.model

import android.graphics.Color

class Basecamp : Tank() {

    override var x = (0..(GameConfig.SCREEN_WIDTH / 2).toInt()).random().toFloat()
    override var color = Color.BLUE
    lateinit var missile: Missile

    fun initMissile() {
        missile = Missile()
        missile.initMissile(x, y)
        missile.v = 10
        missile.angle = 45.0
    }

    override fun setTank(landForm: LandForm) {
        x = ((landForm.vertex[1][0].toInt()..landForm.vertex[2][0].toInt())).random().toFloat()
        y = landForm.isOnLandForm(x)
    }

}