package com.example.example.androidfortress.controller

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.example.androidfortress.view.CanvasView
import com.example.example.androidfortress.model.LandForm
import com.example.example.androidfortress.databinding.ActivityMainBinding
import com.example.example.androidfortress.model.Tank


class MainActivity : AppCompatActivity() {

    // 기기의 가로 세로 높이 구하기
    private var width: Int = 0
    private var height: Int = 0

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Reference View to use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 기기의 가로, 세로 길이 구하기
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y


        val canvasView = CanvasView(this)
        val layoutParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2f)
        canvasView.layoutParams = layoutParam
        binding.layout.addView(canvasView, 0)



        val landForm = LandForm(width, height)
        landForm.setLand()
        val v = landForm.vertex
        v.forEach {
            Log.e("vertex in Main", it.joinToString(" "))
        }

        canvasView.setVertex(v)



        Log.e("layout height", "$width $height ${height*0.8}")

        val tank = Tank()
        tank.setTank(v)
        canvasView.setBasecamp(tank)

        val tank1 = Tank()
        tank1.setTank(v)

        val tank2 = Tank()
        tank2.setTank(v)

        val tank3 = Tank()
        tank3.setTank(v)

        val enemies = arrayListOf(tank1, tank2, tank3)
        canvasView.setEnemies(enemies)

    }

}