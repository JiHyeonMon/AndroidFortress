package com.example.example.androidfortress.model

class LandForm(width: Int, height: Int) {

    val width = width
    val height = height
    var vertex: MutableList<ArrayList<Int>> = mutableListOf(arrayListOf(0, height/2))


    fun setLand() {
        // 몇개의 꼭지점을 갖는 지형을 그릴지
        val vertexNum = (4..6).random()


        for (i in 0..vertexNum) {
//            val x = (0..width).random()
            val x = (i + 1) * width / vertexNum

            // 왜 3ㅈ따리

            val y = if (i % 2 == 0) {
                ((height * 0.1).toInt()..(height * 0.3).toInt()).random()
            } else {
                ((height * 0.6).toInt()..(height * 0.75).toInt()).random()
            }

//            val y = ((height * 0.1).toInt()..(height * 0.8).toInt()).random()

            vertex.add(arrayListOf(x, y))
        }

        val sortedVertex = vertex.sortedBy { it -> it[0] }


//        vertex.forEach{
//            Log.e("vertex", it.joinToString(" "))
//        }
//
//        sortedVertex.forEach {
//            Log.e("vertex", it.joinToString(" "))
//
//        }

        vertex = sortedVertex as MutableList<ArrayList<Int>>

    }
}