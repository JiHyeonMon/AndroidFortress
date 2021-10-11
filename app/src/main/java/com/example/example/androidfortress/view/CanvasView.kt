package com.example.example.androidfortress.view

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import com.example.example.androidfortress.model.Tank

class CanvasView(context: Context) : View(context) {

    private val path = Path()
    private val paint = Paint()

    private var vertex: MutableList<ArrayList<Int>>? = null
    private var basecamp: Tank? = null
    private var enemies: ArrayList<Tank>? = null

    fun setVertex(vertex: MutableList<ArrayList<Int>>) {
        this.vertex = vertex
    }

    fun setBasecamp(tank: Tank) {
        this.basecamp = tank
    }

    fun setEnemies(tanks: ArrayList<Tank>) {
        this.enemies = tanks
    }

    override fun onDraw(canvas: Canvas?) {

        //draw here...

        canvas!!.drawColor(Color.WHITE)

        paint.strokeWidth = 5f
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE

        paint.pathEffect = CornerPathEffect(100F)

        //nothing to draw...
        if (this.vertex != null) {
            path.reset()
            path.lineTo(0F, height.toFloat())

//
//
            for (i in 0 until vertex!!.size - 1) {
//            path.quadTo(vertex[i][0].toFloat(), vertex[i][1].toFloat(), vertex[i+1][0].toFloat(), vertex[i+1][1].toFloat())
                path.lineTo(vertex!![i][0].toFloat(), vertex!![i][1].toFloat())
            }

            canvas.drawPath(path, paint)

            Log.e("second", "$width $height")
        }

        if (this.basecamp != null) {
            paint.color = Color.BLUE
            canvas.drawRect((basecamp!!.x-35).toFloat(), (basecamp!!.y-35).toFloat(),
                (basecamp!!.x+35).toFloat(), (basecamp!!.y+35).toFloat(), paint)
        }

        if (this.enemies != null) {
            paint.color = Color.RED
            enemies!!.forEach {
                canvas.drawRect((it!!.x-35).toFloat(), (it!!.y-35).toFloat(),
                    (it!!.x+35).toFloat(), (it!!.y+35).toFloat(), paint)
            }

        }


        // 직선 곡선을 패스로 정의한 후 출력
//        path.reset()
////        path.moveTo(10F, 110F) // 첫 좌표 지정
//        path.quadTo(120F, 170F, 200F, 110F)
//        path.quadTo(250F, 50F, 300F, 300F)
//        path.quadTo(320F, 350F, 320F, 110F)
//        path.quadTo(120F, 170F, 200F, 110F)


    }
}

//        // 곡선 패스 출력
//        path.reset()
//        path.moveTo(10F, 220F)
//        path.cubicTo(80F, 150F, 150F, 220F, 220F, 180F)
//        paint.strokeWidth = 2f
//        paint.color = Color.BLACK
//        canvas.drawPath(path, paint)
//
//        // 곡선 패스 위에 텍스트 출력
//
//        // 곡선 패스 위에 텍스트 출력
//        paint.textSize = 20f
//        paint.style = Paint.Style.FILL
//        paint.isAntiAlias = true
//        canvas.drawTextOnPath("Curved Text on Path.", path, 0F, 0F, paint)