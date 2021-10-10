package com.example.example.androidfortress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import android.view.View

class CanvasView(context: Context, vertex: MutableList<ArrayList<Int>>) : View(context) {

    val path = Path()
    val paint = Paint()
    val vertex = vertex

    override fun onDraw(canvas: Canvas?) {

        canvas!!.drawColor(Color.WHITE)

        paint.strokeWidth = 5f
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE

        // 직선 곡선을 패스로 정의한 후 출력
//        path.reset()
////        path.moveTo(10F, 110F) // 첫 좌표 지정
//        path.lineTo((width/2).toFloat(), (height).toFloat())
//        path.lineTo((width/2+100).toFloat(), (height.toFloat()))
//
//        path.lineTo(50F, 150F)
////        path.rLineTo(50F, -30F) // r 붙으면 상대 좌표로
//        path.quadTo(120F, 170F, 200F, 110F)
//        path.quadTo(250F, 50F, 300F, 300F)
//        path.quadTo(320F, 350F, 320F, 110F)
//        path.quadTo(120F, 170F, 200F, 110F)

        path.reset()
        path.lineTo(0F, height.toFloat())

//
//
        for (i in 1 until vertex.size-1 step 2) {
            path.quadTo(vertex[i][0].toFloat(), vertex[i][1].toFloat(), vertex[i+1][0].toFloat(), vertex[i+1][1].toFloat())
        }

        canvas.drawPath(path, paint)

        Log.e("second", "$width $height")


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