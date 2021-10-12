package com.example.example.androidfortress.model

class LandForm(height: Float) {

    private val width = GameConfig.SCREEN_WIDTH
    private val height = GameConfig.SCREEN_HEIGHT
    var vertex: MutableList<ArrayList<Float>> = mutableListOf(arrayListOf(0f, height/2))

    fun setLand() {
        // 몇개의 꼭지점을 갖는 지형을 그릴지
        val vertexNum = (4..6).random()


        for (i in 0..vertexNum) {
//            val x = (0..width).random()
            val x = (i + 1) * this.width / vertexNum

            val y  = if (i % 2 == 0) {
                ((this.height * 0.1).toInt()..(this.height * 0.3).toInt()).random().toFloat()
            } else {
                ((this.height * 0.6).toInt()..(this.height * 0.75).toInt()).random().toFloat()
            }

            vertex.add(arrayListOf(x, y))
        }

        val sortedVertex = vertex.sortedBy { it -> it[0] }

        vertex = sortedVertex as MutableList<ArrayList<Float>>

    }

}