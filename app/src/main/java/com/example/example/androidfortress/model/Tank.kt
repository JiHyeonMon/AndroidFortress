package com.example.example.androidfortress.model

import android.graphics.Color
import kotlin.properties.Delegates

open class Tank {

    // 탱크 위치의 x 좌표
    // 기본으로 화면의 우측에 위치하기 위해서 기기 화면 사이즈의 중간~끝까지의 길이 중 랜덤한 곳에 위치
    open var x = ((GameConfig.SCREEN_WIDTH/2).toInt()..GameConfig.SCREEN_WIDTH.toInt()).random().toFloat()

    // 탱크 위치의 y 좌표 - 산의 좌표를 가져와 추후 계산
    var y by Delegates.notNull<Float>()

    // 탱크는 색상을 가진다. 빨간색으로 지정
    open var color = Color.RED

    // 탱크 위치 초기화
    open fun initTank(mountain: Mountain) {
        // 랜덤한 위치를 갖는 탱크의 x 좌표에 맞는 y 값을 구해서 완전한 위치를 설정한다.
        y = mountain.getMountainY(x)
    }
}