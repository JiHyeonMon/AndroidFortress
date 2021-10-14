package com.example.example.androidfortress.model

import android.graphics.Color
import kotlin.math.abs
import kotlin.math.sqrt

class Game {

    // 게임상태를 나타내는 상수
    enum class GAME_STATE { READY, FIRE, FINISHED }

    // 현재 게임의 상태를 나타낼 state 변수
    lateinit var gameState: GAME_STATE

    // 발사 가능한 미사일 수를 나타낼 변수
    // 초기 값은 GameConfig에서 지정
    var missileNum = GameConfig.MISSILE_NUMBER

    // < 게임 객체 선언 >
    // 산(지형) 선언
    lateinit var mountain: Mountain

    // 나의 탱크 - Basecamp 선언
    lateinit var basecamp: Basecamp

    // 적의 탱크는 한 대가 아니라 여러 대라 enemies라는 이름의 리스트로 만들어 넣는다.
    var enemies: ArrayList<Tank> = arrayListOf()


    /***********************************************************************************************
     * Public Method
     */
    // 게임에 필요한 변수 및 게임 객체들 초기화
    fun init() {
        // TODO 게임 상태 선언 - 매번? - 개념이 이상하다.
        // 게임 상태를 발사 가능한 상태인 READY로 설정
        gameState = GAME_STATE.READY

        // 산 초기화 - 객체 생성 및 산 그리기 위한 꼭지점 좌표 리스트 생성
        mountain = Mountain()
        mountain.initMountain()

        // 나의 tank - Basecamp 초기화
        basecamp = Basecamp()
        // 초기화시, x 좌표에 대한 산의 y 좌표가 필요하기에 mountain을 매개변수로 전달
        basecamp.initTank(mountain)


        // 적의 탱크 초기화, 초기 위치 설정
        // ENEMY_NUMBER 만큼의 적 생성하여 리스트에 저장
        for (i in 0 until GameConfig.ENEMY_NUMBER) {
            val tank = Tank()
            tank.initTank(mountain)
            enemies.add(tank)
        }

        // 미사일 초기화
        basecamp.initMissile()

        // Basecamp에서 미사일 각도 보여주기 위한 가이드 라인 생성
        basecamp.setGuide()

    }

    // 미사일 발사
    //
    fun missileProgress() {

        // Change Game State
        gameState = GAME_STATE.FIRE

        // Missile Move
        // 미사일 정해진 포물선 궤도를 따라 좌표 변화
        basecamp.missile.move()

        // < 발사한 미사일 체크 >
        // 체크 1 - 산에 맞았는가
        if (isHitMountain()) {
            // isTrue - 산에 맞았다면
            missileConsumed()
        }

        // 체크 2 - 화면 가로 너비에서 벗어났는가
        if (isOverScreen()) {
            // isTrue - 화면 가로로 벗어났다면
            missileConsumed()
        }

        // 체크 3 - 적을 맞췄는가
        if (isHitEnemy() != null) {
            // is not null, return Tank
            // 적을 맞췄다면, 맞춘 Tank 반환하는데 해당 적 탱크 받아와서 색깔을 Black 으로 바꿔준다.
            val hitEnemy = isHitEnemy()
            hitEnemy!!.color = Color.BLACK

            // not failed - 이후에 계속 떨어지며 더 맞출 수도 있다.
            // 계속 진행되게 return 해준다.
            return
        }

        // 체크 4 - 땅에 떨어졌는가
        if (isDrop()) {
            // isTrue
            missileConsumed()
        }

    }

    /***********************************************************************************************
     * Private Method
     */
    // 미사일 체크 1 - 산(지형)에 닿았는가
    private fun isHitMountain(): Boolean {
        // 원형의 미사일과 직선이 닿였는가에 대한 판단은 직선과 한 점의 거리로 판단한다.
        // 한 점 (미사일의 중심) 과 산(직선)사이의 거리 d 가 미사일의 반지름보다 작다면 닿였다고 판단한다.

        // 직선의 방정식을 구하기 위한 두 점의 좌표
        var x1 = 0f
        var x2 = 0f
        var y1 = 0f
        var y2 = 0f

        // 우측으로 발사 - 3번째 선분부터 검사한다.
        for (i in 3 until mountain.vertex.size) {
            if (mountain.vertex[i][0] > basecamp.missile.x) {
                x1 = mountain.vertex[i - 1][0]
                y1 = mountain.vertex[i - 1][1]
                x2 = mountain.vertex[i][0]
                y2 = mountain.vertex[i][1]

                break
            }
        }

        // 두 점을 지나는 직선의 방정식
        // y = (y2 - y1) / (x2 - x1) * (x - x1) + y1
        // (y - y1) = (y2 - y1) / (x2 - x1) * (x - x1)
        // (y - y1) = { (y2 - y1) / (x2 - x1) * x } - { (y2 - y1) / (x2 - x1) * x1 }

        // (y2 - y1) / (x2 - x1)* x - y - (y2 - y1) / (x2 - x1) * x1 + y1 = 0
        //  일반식 ax + by + c = 0 형태로 바꾸자면
        // a = (y2 - y1) / (x2 - x1)
        // b = -1
        // c = - (y2 - y1) / (x2 - x1) * x1 + y1

        val a = (y2 - y1) / (x2 - x1)
        val b = -1
        val c = -(y2 - y1) / (x2 - x1) * x1 + y1

        //한 점 (미사일의 중심) 과 산(직선)사이의 거리 d
        val d = abs(a * basecamp.missile.x + b * basecamp.missile.y + c) / sqrt(a * a + b * b)

        //d 가 미사일의 반지름보다 작다면 닿였다고 판단한다.
        if (d < GameConfig.MISSILE_SIZE) {
            return true
        }
        return false
    }

    // 미사일 체크 2 - 화면 가로 너비에서 벗어났는가
    private fun isOverScreen(): Boolean {
        // 미사일의 중심 x좌표에서 미사일 사이즈를 빼서 ""미사일의 왼쪽 좌표"" 가 스크린 너비를 넘어섰다면, 더 이상 화면에 미사일이 보이지 않게 된다.
        if (basecamp.missile.x - GameConfig.MISSILE_SIZE > GameConfig.SCREEN_WIDTH) {
            return true
        }
        return false
    }

    // 미사일 체크 3 - 적을 맞췄는지 확인
    private fun isHitEnemy(): Tank? {
        // TODO 계산 로직 더 심플하게
        // 적을 맞췄다면 어떤 적을 맞췄는지 Tank객체를 반환한다. (안맞췄다면 null 반환)
        // 적 enemies 리스트 반복문을 통해 돌며 하나하나 확인한다.

        // 탱크가 미사일에 맞는 경우는 크게 3가지가 있다.
        // 1. 탱크의 왼편에 미사일의 오른편이 맞는 경우
        //      1-1. 미사일이 위에서 떨어지며 미사일의 아래가 탱크를 맞추는 경우
        //      1-2. 미사일이 아래서 위로 발사되며 미사일의 윗부분이 탱크를 맞추는 경우
        // 2. 탱크의 오른편에 미사일의 왼편이 맞는 경우 (미사일 아래가 탱크를 맞춤)
        enemies.forEach {
            // 1. 미사일의 오른편이 탱크의 왼편과 만날 때
            if (it.x - GameConfig.TANK_SIZE <= basecamp.missile.x + GameConfig.MISSILE_SIZE && basecamp.missile.x + GameConfig.MISSILE_SIZE <= it.x + GameConfig.TANK_SIZE) {
                // 미사일 가장 오른 x 좌표가 탱크 너비 안에 들어올 때

                if (it.y - GameConfig.TANK_SIZE <= basecamp.missile.y + GameConfig.MISSILE_SIZE && basecamp.missile.y + GameConfig.MISSILE_SIZE <= it.y + GameConfig.TANK_SIZE) {
                    // 1-1. 미사일이 위에서 아래로 떨어지며 미사일의 제일 아래부분이 탱크와 겹침
                    return it
                }

                if (it.y - GameConfig.TANK_SIZE <= basecamp.missile.y - GameConfig.MISSILE_SIZE && basecamp.missile.y - GameConfig.MISSILE_SIZE <= it.y + GameConfig.TANK_SIZE) {
                    // 1-2. 미사일이 아래에서 위로 날아가며 미사일의 제일 윗 부분이 탱크와 겹침
                    return it
                }

            }
            // 2. 미사일이 높게 떴다가 내려가며 미사일의 왼편이 탱크와 만날 때
            else if (
                it.x - GameConfig.TANK_SIZE <= basecamp.missile.x - GameConfig.MISSILE_SIZE && basecamp.missile.x - GameConfig.MISSILE_SIZE <= it.x + GameConfig.TANK_SIZE
            ) {
                // 이 경우는 미사일이 위에서 아래로 떨어지는 경우 뿐
                if (it.y - GameConfig.TANK_SIZE <= basecamp.missile.y + GameConfig.MISSILE_SIZE && basecamp.missile.y + GameConfig.MISSILE_SIZE <= it.y + GameConfig.TANK_SIZE) {
                    // 미사일이 위에서 아래로 떨어지며 미사일의 제일 아래부분이 탱크와 겹침
                    return it
                }
            }
        }

        // 아무 탱크와도 만나지 않으면 null 반환
        return null
    }

    // 미사일 체크 4 - 미사일이 아무것도 못맞추고 땅에 떨어진 경우
    private fun isDrop(): Boolean {
        // 미사일의 중심 y좌표와 기기의 높이를 비교
        if (basecamp.missile.y > GameConfig.SCREEN_HEIGHT){
            return true
        }
        return false
    }

    // 미사일 발포가 끝
    // 다음 발사를 준비한다.
    private fun missileConsumed() {
        // 남은 미사일 수가 없다면
        if (missileNum == 0) {
            // 게임 상태를 FINISHED로 바꾸고 리턴하여 게임을 종료한다.
            gameState = GAME_STATE.FINISHED
            return
        }

        // 게임 상태를 FIRE -> READY로 바꾸고 발사 가능 상태로 바꾼다.
        gameState = GAME_STATE.READY
        // 미사일을 새로 장전한다.
        basecamp.initMissile()
    }

}