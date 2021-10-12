package com.example.example.androidfortress.model

class Basecamp : Tank() {

    override var x = (0..(GameConfig.SCREEN_WIDTH/2).toInt()).random().toFloat()


}