package com.example.example.androidfortress.controller

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.example.androidfortress.databinding.ActivityMainBinding
import com.example.example.androidfortress.model.Game
import com.example.example.androidfortress.view.CanvasView


class MainActivity : AppCompatActivity() {

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
            game.basecamp.setGuide()
            updateUI()

        }
        binding.btnDirectionDown5.setOnClickListener {
            game.basecamp.missile.changeAngle(-5)
            game.basecamp.setGuide()
            updateUI()
        }
        binding.btnPowerUp.setOnClickListener {
            game.basecamp.missile.changeSpeed(1)
            game.basecamp.setGuide()
            updateUI()
        }
        binding.btnPowerDown.setOnClickListener {
            game.basecamp.missile.changeSpeed(-1)
            game.basecamp.setGuide()
            updateUI()
        }

    }

    //
    private fun initUI() {

        canvasView = CanvasView(this)
        val layoutParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            2f
        )
        canvasView.layoutParams = layoutParam
        binding.layout.addView(canvasView, 0)

        game.mountain.vertex.forEach {
            Log.e("vertex", it.joinToString(" "))
        }

        canvasView.setVertex(game.mountain.vertex)
        canvasView.setBasecamp(game.basecamp)
        canvasView.setEnemies(game.enemies)
        canvasView.setMissile(game.basecamp.missile)
        canvasView.setGuide(arrayOf(game.basecamp.guideX, game.basecamp.guideY))
    }

    //
    private fun progress() {
        handler = Handler()
        runnable = Runnable {

            // 게임 모델에서 데이터 변경
            // gameState = FIRE 가 된다.
            // 미사일 날아간다.
            game.progress()

            // ViewUpdate
            updateUI()

            // check
            // 미사일이 적에게 닿았는지
            if (game.gameState != Game.GAME_STATE.FIRE) {
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
        // View 업데이트
        binding.textRemainBomb.text = game.missileNum.toString()
        binding.textAngle.text = game.basecamp.missile.angle.toString()
        binding.textPower.text = game.basecamp.missile.V.toString()


        canvasView.setMissile(game.basecamp.missile)
        canvasView.setGuide(arrayOf(game.basecamp.guideX, game.basecamp.guideY))
        canvasView.invalidate()
    }
}