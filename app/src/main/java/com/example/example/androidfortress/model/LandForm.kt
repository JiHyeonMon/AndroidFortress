package com.example.example.androidfortress.model

class LandForm() {

    private val width = GameConfig.SCREEN_WIDTH
    private val height = GameConfig.SCREEN_HEIGHT

    lateinit var vertex: MutableList<ArrayList<Float>>

    fun setLand() {
        // 몇개의 꼭지점을 갖는 지형을 그릴지
        val vertexNum = (4..6).random()
        vertex = mutableListOf(
            arrayListOf(0f, height / 2)
        )

        for (i in 0 until vertexNum) {
//            val x = (0..width).random()
            val x = (i + 1) * this.width / vertexNum

            val y = if (i % 2 == 0) {
                ((this.height * 0.1).toInt()..(this.height * 0.5).toInt()).random().toFloat()
            } else {
                ((this.height * 0.6).toInt()..(this.height * 0.75).toInt()).random().toFloat()
            }

            vertex.add(arrayListOf(x, y))
        }

        val sortedVertex = vertex.sortedBy { it -> it[0] }

        vertex = sortedVertex as MutableList<ArrayList<Float>>

    }

    // 지형 위에 있는가
    // x좌표를 받으면 해당 x좌표에 해당하는 y 좌표를 리턴한다.
    fun isOnLandForm(x: Float): Float {

        var x1 = 0f
        var x2 = 0f
        var y1 = 0f
        var y2 = 0f

        for (i in vertex.indices) {
            if (vertex[i][0] > x) {
                x1 = vertex[i - 1][0]
                y1 = vertex[i - 1][1]
                x2 = vertex[i][0]
                y2 = vertex[i][1]
                break
            }
        }

        // 두 점을 지나는 직선의 방정식
        return (y2 - y1) / (x2 - x1) * (x - x1) + y1

    }

}