package com.example.example.androidfortress.view

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import com.example.example.androidfortress.model.Tank

class CanvasView(context: Context) : View(context) {

    private val landformPath = Path()
    private val paint = Paint()

    private var vertex: MutableList<ArrayList<Float>>? = null
    private var basecamp: Tank? = null
    private var enemies: ArrayList<Tank>? = null

    fun setVertex(vertex: MutableList<ArrayList<Float>>) {
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

        //nothing to draw...
        if (this.vertex != null) {
            paint.strokeWidth = 5f
            paint.color = Color.GREEN
            paint.style = Paint.Style.STROKE
            landformPath.reset()
//            path.lineTo(0F, height.toFloat())
            paint.pathEffect = CornerPathEffect(100f)

            for (i in 0 until vertex!!.size - 1) {
//            path.quadTo(vertex[i][0].toFloat(), vertex[i][1].toFloat(), vertex[i+1][0].toFloat(), vertex[i+1][1].toFloat())
                landformPath.lineTo(vertex!![i][0], vertex!![i][1])
            }

            canvas.drawPath(landformPath, paint)

            Log.e("second", "$width $height")
        }

        // 기존에 경로 그릴때 라운드 넣었는데 이제 라운드 없이 네모 그리기 위해 0
        paint.pathEffect = CornerPathEffect(0F)
        // 이제 그릴 rectangle 꽉 채워 그리기 위해 paint style 값 변경
        paint.style = Paint.Style.FILL_AND_STROKE


        if (this.basecamp != null) {
            paint.color = Color.BLUE
            canvas.drawRect((basecamp!!.x-20), (basecamp!!.y), (basecamp!!.x+20), (basecamp!!.y+40), paint)
        }

        if (this.enemies != null) {
            paint.color = Color.RED
            enemies!!.forEach {
                canvas.drawRect((it.x-20), (it.y), (it.x+20), (it.y+40), paint)
//                canvas.drawOval((it!!.x-5).toFloat(), (it!!.y).toFloat(),
//                    (it!!.x+5).toFloat(), (it!!.y).toFloat(), paint)
//                tankPath.moveTo(it.x1.toFloat(), it.y1.toFloat() )
//                tankPath.lineTo(it.x.toFloat(), it.y.toFloat())
//                canvas.drawPath(tankPath, paint)
            }

        }

    }
}