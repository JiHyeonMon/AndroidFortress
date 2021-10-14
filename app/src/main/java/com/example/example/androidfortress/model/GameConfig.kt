package com.example.example.androidfortress.model

object GameConfig {

    // 기기 화면 사이즈 지정
    var SCREEN_WIDTH = 1080F
    var SCREEN_HEIGHT = 1794F

    // 최대 발사 가능 미사일 수
    const val MISSILE_NUMBER = 5
    // 적의 탱크 수
    const val ENEMY_NUMBER = 3

    // View의 Canvas에서 그릴 탱크와 미사일 사이즈
    // 탱크와 미사일은 각각 좌표를 가진다. 해당 좌표에 아래 크기만큼의 크기로 화면에 그린다.
    const val TANK_SIZE = 40
    const val MISSILE_SIZE = 20

    // Basecamp에서 미사일 방향(angle)에 대한 가이드를 그려준다.
    // 중심점으로 부터 아래 반지름 값만큼 길이로 그린다.
    const val MISSILE_GUIDE_RADIUS = 100

    // 미사일 위치 계산에 필요한 시간 - seconds
    const val SECONDS_TIME = 0.05

    // MainActivity에서 미사일 움직임 반영할 Timer로 미사일 위치 계산에 사용된 시간과 같은 값 필요 - 단위 milliseconds
    const val MILLIS_TIME = SECONDS_TIME * 1000

    // 미사일 위치 계산에 필요한 중력 크기
    const val G = 9.8

}