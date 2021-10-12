package com.example.example.androidfortress.controller

import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.example.androidfortress.view.CanvasView
import com.example.example.androidfortress.model.LandForm
import com.example.example.androidfortress.databinding.ActivityMainBinding
import com.example.example.androidfortress.model.Game
import com.example.example.androidfortress.model.GameConfig
import com.example.example.androidfortress.model.Tank


class MainActivity : AppCompatActivity() {

    // 기기의 가로 세로 높이 구하기
    private var width: Int = 0
    private var height: Int = 0

    // 실행할 게임 객체
    lateinit var game: Game

    lateinit var canvasView: CanvasView

    lateinit var handler: Handler
    lateinit var runnable: Runnable

    private lateinit var binding: ActivityMainBinding

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Reference View to use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        game = Game()
        game.init()

        initUI()

    }

    //
    private fun initUI() {
        // 기기의 가로, 세로 길이 구하기
//        val display = windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//        Log.e("screen", "${size.x} ${size.y}")
//
//        GameConfig.SCREEN_WIDTH = size.x.toFloat()
//        GameConfig.SCREEN_HEIGHT = size.y.toFloat()


        canvasView = CanvasView(this)
        val layoutParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2f)
        canvasView.layoutParams = layoutParam
        binding.layout.addView(canvasView, 0)

        game.landForm.vertex.forEach {
            Log.e("vertex", it.joinToString(" "))
        }
        canvasView.setVertex(game.landForm.vertex)
        canvasView.setBasecamp(game.basecamp)
        canvasView.setEnemies(game.enemies)
    }

    //
    private fun progress() {
        handler = Handler()
        runnable = Runnable {
            // TODO

            // game.progress()
            // 게임 모델에서 데이터 변경

            // ViewUpdate
            updateUI()

            // check
            // 미사일이 적에게 닿았는지

            // 1초마다 check
            handler.postDelayed(runnable, 1000)
        }
        handler.post(runnable)
    }

    //
    private fun updateUI() {

    }

}