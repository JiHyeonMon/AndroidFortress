package com.example.example.androidfortress.model

import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

class Missile {

    // 미사일 위치의 x 좌표, y 좌표
    var x by Delegates.notNull<Double>()
    var y by Delegates.notNull<Double>()

    // 미사일 발사시 초기 속력
    var V by Delegates.notNull<Int>()

    // 포물선 궤도 구할 때 사용될 중력 - GameConfig에서 지정 9.8
    private val G = GameConfig.G

    // 미사일 발사시 각도
    var angle by Delegates.notNull<Double>()

    // 미사일 발사 이후의 시간 - 포물선 궤도 구할 때 사용
    var t = 0.0

    // 미사일 발사! 미사일의 좌표가 포물선 궤도를 그리며 이동한다.
    fun move() {
        // TODO 계산식 잘못됨
        // 해당 시간이 흐른 만큼의 거리 계산
        t += GameConfig.SECONDS_TIME

        // 해당 시간이 흐른 만큼 변화된 x, y 좌표를 구한다.  << 이동한 거리 = 속력 * 시간 >>
        // 방향이 비스듬하게 올려 쏘아지기 때문에
        // x 방향의 속력(V * cos(Math.toRadians(angle))),
        // y 방향의 속력(V * sin(Math.toRadians(angle)))을 따로 구한다.
        val dx = V * cos(Math.toRadians(angle)) * t
        // y 방향의 경우, 중력이 작용하기 때문에, 온전히 위로 올라간 거리에서 중력으로 아래로 이동한 거리를 빼서 계산한다.
        val dy = V * sin(Math.toRadians(angle)) * t - G * t * t / 2

        // 변화된 위치만큼 이동시킨다.
        x += dx
        y -= dy

    }

    // 미사일 발사 각도 변화
    fun changeAngle(da: Int) {
        this.angle += da.toDouble()
    }

    // 미사일 발사시 초기 속력 변화
    fun changeSpeed(dv: Int) {
        this.V += dv
    }
}