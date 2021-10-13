package com.example.example.androidfortress.view

import android.content.Context
import android.graphics.*
import android.view.View
import com.example.example.androidfortress.model.GameConfig
import com.example.example.androidfortress.model.Missile
import com.example.example.androidfortress.model.Tank

class CanvasView(context: Context) : View(context) {

    private val landformPath = Path()

    private var vertex: MutableList<ArrayList<Float>>? = null
    private var basecamp: Tank? = null
    private var enemies: ArrayList<Tank>? = null
    private var missile: Missile? = null

    fun setVertex(vertex: MutableList<ArrayList<Float>>) {
        this.vertex = vertex
    }

    fun setBasecamp(tank: Tank) {
        this.basecamp = tank
    }

    fun setEnemies(tanks: ArrayList<Tank>) {
        this.enemies = tanks
    }

    fun setMissile(missile: Missile) {
        this.missile = missile
    }

    override fun onDraw(canvas: Canvas?) {

        //draw here...
        canvas!!.drawColor(Color.WHITE)

        //nothing to draw...
        if (this.vertex != null) {
            val paint = Paint()
            paint.strokeWidth = 5f
            paint.color = Color.GREEN
            paint.style = Paint.Style.STROKE
            paint.pathEffect = CornerPathEffect(100f)

            val noCurvePaint = Paint()
            noCurvePaint.strokeWidth = 5f
            noCurvePaint.color = Color.LTGRAY
            noCurvePaint.style = Paint.Style.STROKE

            landformPath.reset()
            for (i in 0 until vertex!!.size) {
                landformPath.lineTo(vertex!![i][0], vertex!![i][1])
            }
            canvas.drawPath(landformPath, paint)
            canvas.drawPath(landformPath, noCurvePaint)
        }

        if (this.basecamp != null) {
            val paint = Paint()
            // 이제 그릴 rectangle 꽉 채워 그리기 위해 paint style 값 변경
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.color = basecamp!!.color
            canvas.drawRect((basecamp!!.x-GameConfig.TANK_SIZE), (basecamp!!.y-GameConfig.TANK_SIZE), (basecamp!!.x+GameConfig.TANK_SIZE), (basecamp!!.y+GameConfig.TANK_SIZE), paint)
        }

        if (this.enemies != null) {
            val paint = Paint()
            // 이제 그릴 rectangle 꽉 채워 그리기 위해 paint style 값 변경
            paint.style = Paint.Style.FILL_AND_STROKE
            enemies!!.forEach {
                paint.color = it.color
                canvas.drawRect((it.x- GameConfig.TANK_SIZE), (it.y-GameConfig.TANK_SIZE), (it.x+GameConfig.TANK_SIZE), (it.y+GameConfig.TANK_SIZE), paint)
            }

        }

        if (this.missile != null) {
            val paint = Paint()
//            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.ADD)
            paint.color = Color.BLACK
            canvas.drawOval(((missile!!.x-GameConfig.MISSILE_SIZE).toFloat()), (missile!!.y.toFloat()-GameConfig.MISSILE_SIZE), ((missile!!.x+GameConfig.MISSILE_SIZE).toFloat()), ((missile!!.y+GameConfig.MISSILE_SIZE).toFloat()), paint)
        }
    }
}