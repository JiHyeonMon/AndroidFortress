package com.example.example.androidfortress.controller

import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
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

        binding.btnFire.setOnClickListener {
            game.missileNum -= 1
            progress()
        }
        binding.btnDirectionUp5.setOnClickListener {
            game.basecamp.missile.changeAngle(5)
            binding.textAngle.text = game.basecamp.missile.angle.toString()
        }
        binding.btnDirectionUp10.setOnClickListener {
            game.basecamp.missile.changeAngle(10)
            binding.textAngle.text = game.basecamp.missile.angle.toString()

        }
        binding.btnDirectionDown5.setOnClickListener {
            game.basecamp.missile.changeAngle(-5)
            binding.textAngle.text = game.basecamp.missile.angle.toString()

        }
        binding.btnDirectionDown10.setOnClickListener {
            game.basecamp.missile.changeAngle(-10)
            binding.textAngle.text = game.basecamp.missile.angle.toString()

        }
        binding.btnPowerUp.setOnClickListener {
            game.basecamp.missile.changeSpeed(1)
            binding.textPower.text = game.basecamp.missile.v.toString()

        }
        binding.btnPowerDown.setOnClickListener {
            game.basecamp.missile.changeSpeed(-1)
            binding.textPower.text = game.basecamp.missile.v.toString()
        }

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
        val layoutParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            2f
        )
        canvasView.layoutParams = layoutParam
        binding.layout.addView(canvasView, 0)

        game.landForm.vertex.forEach {
            Log.e("vertex", it.joinToString(" "))
        }
        canvasView.setVertex(game.landForm.vertex)
        canvasView.setBasecamp(game.basecamp)
        canvasView.setEnemies(game.enemies)
        canvasView.setMissile(game.basecamp.missile)
    }

    //
    private fun progress() {
        handler = Handler()
        runnable = Runnable {
            // TODO

            // 게임 모델에서 데이터 변경
            // gameState = FIRE 가 된다.
            // 미사일 날아간다.
            game.progress()

            // ViewUpdate
            updateUI()

            // check
            // 미사일이 적에게 닿았는지
            if (game.gameState!=Game.GAME_STATE.FIRE) {
                Toast.makeText(this, "한 발 끝 ", Toast.LENGTH_SHORT).show()
                updateUI()
                return@Runnable
            }

            // 1초마다 check
            handler.postDelayed(runnable, 50)
        }
        handler.post(runnable)
    }

    //
    private fun updateUI() {
        binding.textRemainBomb.text = game.missileNum.toString()
        binding.textAngle.text = game.basecamp.missile.angle.toString()
        binding.textPower.text = game.basecamp.missile.v.toString()


        canvasView.setMissile(game.basecamp.missile)
        canvasView.invalidate()
    }
}