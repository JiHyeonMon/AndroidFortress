package com.example.example.androidfortress.model

import android.graphics.Color
import kotlin.math.cos
import kotlin.math.sin
import kotlin.properties.Delegates

class Basecamp : Tank() {

    // 탱크는 기본적으로 빨강색을 가지지만, 나의 탱크(베이스캠프)는 파란색을 가지기 위해 오버라이드 해서 설정
    override var color = Color.BLUE

    // 베이스캠프는 미사일을 가진다.
    lateinit var missile: Missile

    // 미사일 각도에 대한 가이드라인을 표시하기 위한 가이드 점이 존재한다.
    // 해당 점의 x, y 좌표 선언
    var guideX by Delegates.notNull<Double>()
    var guideY by Delegates.notNull<Double>()

    // 탱크 위치 초기화
    override fun initTank(mountain: Mountain) {
        // 탱크 위치의 x 좌표
        // 기본으로 화면의 우측의 랜덤한 좌표를 갖지만, 베이스 캠프는 나의 탱크로 화면의 좌측에 위치한다.
        // 좌측에서도 /\ 에서 \ 부분에 위치할 수 있게 위치를 설정한다. vertex의 index 1,2 선분에 위치
        x = ((mountain.vertex[1][0].toInt()..mountain.vertex[2][0].toInt())).random().toFloat()

        // 해당 x 값에 대한 y 값을 구해서 완전한 위치 설정
        y = mountain.getMountainY(x)
    }

    // 미사일을 초기화한다.
    fun initMissile() {
        // 미사일 객체 생성
        missile = Missile()

        // 미사일의 초기 위치를 베이스 캠프의 중심점으로 설정
        // 미사일의 처음 속도, 각도 지정
        missile.x = this.x.toDouble()
        missile.y = this.y.toDouble()
        missile.V = 20
        missile.angle = 45.0
    }


    // 베이스 캠프에서 미사일을 발사시 각도에 대한 가이드라인을 보여주는데 해당 가이드 설정
    // 미사일의 각도가 변할 때마다 가이드라인도 변한다.
    fun setGuide() {
        // 탱크 중심에서 미사일의 각도만큼의 선을 표시할 것
        // 탱크 중심 좌표, 반지름 값 정해져 있으니 각도에 따른 x, y 좌표를 구해 가이드 좌표로 설정한다.

        // tank의 중심좌표 x, y -> lineTo() -> 미사일 각도를 가지며 반지름만큼 떨어진 곳에 위치한 가이드 점의 좌표 설정
        guideX = x + cos(Math.toRadians(missile.angle)) * GameConfig.MISSILE_GUIDE_RADIUS
        guideY = y - sin(Math.toRadians(missile.angle)) * GameConfig.MISSILE_GUIDE_RADIUS

    }
}