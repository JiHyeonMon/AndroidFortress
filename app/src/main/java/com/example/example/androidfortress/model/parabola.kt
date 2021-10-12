package com.example.example.androidfortress.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class GameView(context: Context?) : View(context), Runnable {
    private var mPaint: Paint? = null
    var maxS = 0.0
    var maxH = 0.0
    var maxT = 0.0
    var dx: Double
    var dy: Double
    var d: Double
    var obj: MovingObject
    var isDraw = false
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
        mPaint!!.isAntiAlias = true
        canvas.drawColor(Color.GREEN)
        if (isDraw) {
            canvas.drawCircle(obj.X.toFloat(), obj.Y.toFloat(), 10F, mPaint!!)
        }
    }

    // 触笔事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> Thread(this).start()
        }
        return true
    }

    override fun run() {
        var t = 0.0
        while (t < maxT) {
            isDraw = true
            val x = obj.V0 * cos(obj.Sita) * t
            val y = obj.V0 * sin(obj.Sita) * t - 9.8 * t * t / 2
            if (y < 0) {
                t += 0.01
                continue
            }
            obj.X = 10 + d * x
            obj.Y = 480 - 10 - d * y
            try {
                Thread.sleep(10)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            postInvalidate()
            t += 0.01
        }
    }

    init {
        mPaint = Paint()
        obj = MovingObject(10, Math.PI / 4, Color.RED)
        obj.T = 2 * obj.V0 * sin(obj.Sita) / 9.8
        obj.H = (obj.V0 * obj.V0 * sin(obj.Sita) * sin(obj.Sita)
                / (2 * 9.8))
        obj.Smax = (2 * obj.V0 * obj.V0 * sin(obj.Sita)
                * cos(obj.Sita)) / 9.8
        if (obj.Smax > maxS) {
            maxS = obj.Smax
        }
        if (obj.H > maxH) {
            maxH = obj.H
        }
        if (obj.T > maxT) {
            maxT = obj.T
        }
        dx = (800 - 20) / maxS
        dy = (480 - 20) / maxH
        d = min(dx, dy)
    }
}

class MovingObject(
    var V0: Int,
    var Sita: Double, red: Int
) {
    val G = 9.8

    var X = 0.0

    var Y = 0.0

    var Color: Int

    fun GetObjectRectangle(): Rect {
        return Rect(X.toInt() - 3, Y.toInt() - 3, 6, 6)
    }

    var Smax = 0.0

    var H = 0.0

    var T = 0.0

    init {
        Sita = Sita
        Color = red
    }
}