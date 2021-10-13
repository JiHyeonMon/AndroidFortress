package com.example.example.androidfortress.model

import android.graphics.Color
import android.util.Log

class Game {

    enum class GAME_STATE { READY, FIRE, HIT, FINISHED }

    lateinit var gameState: GAME_STATE

    var missileNum = GameConfig.MISSILE_NUMBER

    lateinit var mountain: Mountain
    lateinit var basecamp: Basecamp
    lateinit var enemies: ArrayList<Tank>

    fun init() {
        gameState = GAME_STATE.READY

        // 지형 초기화
        mountain = Mountain()
        mountain.setMountain()

        // 나의 tank - basecamp 초기화
        basecamp = Basecamp()
        basecamp.setTank(mountain)


        // 적의 탱크 초기화
        val tank1 = Tank()
        tank1.setTank(mountain)

        val tank2 = Tank()
        tank2.setTank(mountain)

        val tank3 = Tank()
        tank3.setTank(mountain)

        enemies = arrayListOf(tank1, tank2, tank3)

        // 미사일 초기화
        basecamp.initMissile()

        // ㄱㅏ이드 라인
        basecamp.setGuide()

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
        if (isHitMountain()) {
            failed()
        }

        if (isOverScreen()) {
            failed()
        }

        if (isHitEnemy() != null) {
            val hitEnemy = isHitEnemy()
            hitEnemy!!.color = Color.BLACK

            // not failed - 이후에 계속 떨어지며 더 맞출 수도 있다.
            // 계속 진행되게 return 해준다.
            return
        }

        if (isDrop()) {
            failed()
        }

    }

    private fun isHitMountain(): Boolean {

        if (mountain.isHitMountain(basecamp.missile.x.toFloat(), basecamp.missile.y.toFloat())) {
            Log.e("hit", "hit the mountain")
            return true
        }

        return false
    }

    private fun isOverScreen(): Boolean {
        if (basecamp.missile.x - GameConfig.MISSILE_SIZE > GameConfig.SCREEN_WIDTH) {
            return true
        }
        return false
    }

    private fun isHitEnemy(): Tank? {
        enemies.forEach {
            // 미사일의 오른편이 탱크의 왼편과 만날 때
            if (it.x - GameConfig.TANK_SIZE <= basecamp.missile.x + GameConfig.MISSILE_SIZE && basecamp.missile.x + GameConfig.MISSILE_SIZE <= it.x + GameConfig.TANK_SIZE){
                // 미사일 가장 오른 x 좌표가 탱크 너비 안에 들어올 때

                if (it.y - GameConfig.TANK_SIZE <= basecamp.missile.y+GameConfig.MISSILE_SIZE && basecamp.missile.y+GameConfig.MISSILE_SIZE <= it.y+GameConfig.TANK_SIZE) {
                    // 미사일이 위에서 아래로 떨어지며 미사일의 제일 아래부분이 탱크와 겹침
                    return it
                }

                if (it.y - GameConfig.TANK_SIZE <= basecamp.missile.y-GameConfig.MISSILE_SIZE && basecamp.missile.y-GameConfig.MISSILE_SIZE <= it.y+GameConfig.TANK_SIZE) {
                    // 미사일이 아래에서 위로 날아가며 미사일의 제일 윗 부분이 탱크와 겹침
                    return it
                }

            }
            // 미사일이 높게 떴다가 내려가며 미사일의 왼편이 탱크와 만날 때
            else if (
                it.x - GameConfig.TANK_SIZE <= basecamp.missile.x - GameConfig.MISSILE_SIZE && basecamp.missile.x - GameConfig.MISSILE_SIZE <= it.x + GameConfig.TANK_SIZE
            ) {
                // 이 경우는 미사일이 위에서 아래로 떨어지는 경우 뿐
                if (it.y - GameConfig.TANK_SIZE <= basecamp.missile.y+GameConfig.MISSILE_SIZE && basecamp.missile.y+GameConfig.MISSILE_SIZE <= it.y+GameConfig.TANK_SIZE) {
                    // 미사일이 위에서 아래로 떨어지며 미사일의 제일 아래부분이 탱크와 겹침
                    return it
                }
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