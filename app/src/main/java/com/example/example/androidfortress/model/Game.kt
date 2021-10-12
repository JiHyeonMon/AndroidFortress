package com.example.example.androidfortress.model

class Game {


    lateinit var landForm: LandForm
    lateinit var basecamp: Tank
    lateinit var enemies: ArrayList<Tank>
    lateinit var missile: Missile

    fun init() {
        initLandFrom()
        initBasecamp()
        initEnemies()
    }

    fun progress() {

    }

    private fun initLandFrom() {
        landForm = LandForm(GameConfig.SCREEN_HEIGHT)
        landForm.setLand()
    }

    private fun initBasecamp() {
        basecamp = Basecamp()
        basecamp.setTank(landForm.vertex)

    }

    private fun initEnemies() {

        val tank1 = Tank()
        tank1.setTank(landForm.vertex)

        val tank2 = Tank()
        tank2.setTank(landForm.vertex)

        val tank3 = Tank()
        tank3.setTank(landForm.vertex)

        enemies = arrayListOf(tank1, tank2, tank3)
    }


}