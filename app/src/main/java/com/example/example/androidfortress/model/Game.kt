package com.example.example.androidfortress.model

import android.graphics.Color
import android.util.Log

class Game {

    enum class GAME_STATE { READY, FIRE, HIT, FINISHED }

    lateinit var gameState: GAME_STATE

    var missileNum = GameConfig.MISSILE_NUMBER

    lateinit var landForm: LandForm
    lateinit var basecamp: Basecamp
    lateinit var enemies: ArrayList<Tank>

    fun init() {
        gameState = GAME_STATE.READY

        // 지형 초기화
        landForm = LandForm(GameConfig.SCREEN_HEIGHT)
        landForm.setLand()

        // 나의 tank - basecamp 초기화
        basecamp = Basecamp()
        basecamp.setTank(landForm.vertex)


        // 적의 탱크 초기화
        val tank1 = Tank()
        tank1.setTank(landForm.vertex)

        val tank2 = Tank()
        tank2.setTank(landForm.vertex)

        val tank3 = Tank()
        tank3.setTank(landForm.vertex)

        enemies = arrayListOf(tank1, tank2, tank3)

        // 미사일 초기화
        basecamp.initMissile()
    }


    fun progress() {
        if (missileNum < 0) {
            gameState = GAME_STATE.FINISHED
            return
        }

        // Change Game State
        gameState = GAME_STATE.FIRE

        // Missile Move
        basecamp.missile.move()

        // Check
//        if (isHitMountain()) {
//
//        }

        if (isHitEnemy() != null) {
            val hitEnemy = isHitEnemy()
            hitEnemy!!.color = Color.BLACK
            return
            // not failed - 이후에 계속 떨어지며 더 맞출 수도 있다.
            // 계속 진행되게 return 해준다.
        }

        if (isDrop()) {
            failed()
        }

    }

    private fun isHitMountain(): Boolean {
        return false
    }

    private fun isHitEnemy(): Tank? {
        enemies.forEach {
            if (it.x - GameConfig.TANK_SIZE <= basecamp.missile.x && basecamp.missile.x <= it.x + GameConfig.TANK_SIZE &&
                it.y - GameConfig.TANK_SIZE <= basecamp.missile.y && basecamp.missile.y <= it.y + GameConfig.TANK_SIZE
            ) {
                return it
            }
        }

        return null
    }

    // 미사일이 아무것도 못맞추고 땅에 떨어진 경우
    private fun isDrop(): Boolean {
        if (basecamp.missile.y > GameConfig.SCREEN_HEIGHT * 0.8) {
            return true
        }
        return false
    }

    private fun failed() {
        gameState = GAME_STATE.READY
        basecamp.initMissile()
    }

}