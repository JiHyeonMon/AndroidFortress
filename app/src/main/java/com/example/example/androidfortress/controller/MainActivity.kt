package com.example.example.androidfortress.controller

import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.example.androidfortress.databinding.ActivityMainBinding
import com.example.example.androidfortress.model.*


class MainActivity : AppCompatActivity() {

    /**
     * Data 선언
     */
    // 실제 게임과 관련된 데이터를 가지고 있는 모델 선언
    private lateinit var game: Game

    // 게임 화면을 그릴 View
    // Canvas위에 원, 사각형, 경로 등을 그린다.
    private lateinit var canvas: Canvas
    private lateinit var gameboardBitmap: Bitmap

    // Model을 지속적으로 관찰하고 View를 업데이트 시키기 위한 runnable객체와 Handler
    private lateinit var handler: Handler
    lateinit var runnable: Runnable

    // View 객체를 참조하기 위해 ViewBinding 사용
    // binding 객체 선언
    private lateinit var binding: ActivityMainBinding

    /**
     * Activity LifeCycle Start
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Reference View to use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 게임 생성 및 게임 객체 초기화
        game = Game()
        game.init()

        // View 초기화
        initUI()
        // 모델의 데이터를 가지고 View 초기 화면 그려준다.
        updateUI()

        // Attach Click Listener
        // 미사일 발사
        binding.btnFire.setOnClickListener {
            // 미사일 발사 - 모델에 남은 미사일 수 -1 반영
            game.missileNum -= 1
            // 미사일 발사 진행
            progress()
        }

        // 미사일 각도 수정 +5 , -5
        // 각도 수정시 미사일에 반영 + 가이드 라인 반영 + 화면 업데이트
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

        // 미사일 초기 속도 수정 +1 , -1
        // 속도 수정시 미사일에 반영 + 화면 업데이트
        binding.btnPowerUp.setOnClickListener {
            game.basecamp.missile.changeSpeed(1)
            updateUI()
        }
        binding.btnPowerDown.setOnClickListener {
            game.basecamp.missile.changeSpeed(-1)
            updateUI()
        }
    }

    /**
     * View에 게임 객체 그리기 위해 필요한 초기화 작업 진행
     * Model에서 새로 반영된 값 가져와 화면에 그려줌
     */
    private fun initUI() {

        // 게임 화면을 그릴 canvas 객체 생성
        gameboardBitmap = Bitmap.createBitmap(GameConfig.SCREEN_WIDTH.toInt(), GameConfig.SCREEN_HEIGHT.toInt(), Bitmap.Config.ARGB_8888)
        canvas = Canvas(gameboardBitmap)
        canvas.drawColor(Color.WHITE)
        // View의 게임보드에 초기화한 비트맵을 넣은 캔버스를 그려준다.
        binding.gameBoard.setImageBitmap(gameboardBitmap)
        binding.gameBoard.scaleType = ImageView.ScaleType.FIT_XY

    }

    /**
     * 게임 진행하는 함수
     * Model 값 확인 지속적으로 하며 화면 업데이트
     */
    private fun progress() {
        handler = Handler()
        runnable = Runnable {

            // View에서 발사를 누르면 progress가 진행된다.
            // 미사일 발사하기 전, 발사 가능한지 확인
            if (game.gameState == Game.GAME_STATE.FINISHED) {
                gameFinish()
                return@Runnable
            }

            // 게임 모델에서 데이터 변경
            // gameState = FIRE 가 된다.
            // 미사일 날아간다.
            game.missileProgress()

            // ViewUpdate
            updateUI()

            // check
            // 미사일 발사를 하면 상태가 FIRE가 되는데, FIRE 가 아니게 된 순간, 미사일 종료
            if (game.gameState != Game.GAME_STATE.FIRE) {
                Toast.makeText(this, "한 발 끝 ", Toast.LENGTH_SHORT).show()
                return@Runnable
            }

            // MILLIS_TIME마다 check
            handler.postDelayed(runnable, GameConfig.MILLIS_TIME.toLong())
        }
        handler.post(runnable)
    }

    /**
     * View Update
     * Model에서 새로 반영된 값 가져와 화면에 그려줌
     */
    private fun updateUI() {
        // 모델에서 값 가져와 View 업데이트
        // 변경된 남은 미사일 수 업데이트
        binding.textRemainBomb.text = game.missileNum.toString()
        // 변경된 미사일 각도 업데이트
        binding.textAngle.text = game.basecamp.missile.angle.toString()
        // 변경된 미사일 초기 속도 업데이트
        binding.textPower.text = game.basecamp.missile.V.toString()

        // game 화면 지워준다.
        gameboardBitmap.eraseColor(Color.WHITE)
        binding.gameBoard.invalidate()

        // 변화된 값으로 다시 화면 그린다.
        drawMountain(canvas, game.mountain.vertex)
        drawBasecamp(canvas, game.basecamp)
        drawEnemies(canvas, game.enemies)
        drawMissile(canvas, game.basecamp.missile)
        drawGuide(canvas, game.basecamp, arrayOf(game.basecamp.guideX, game.basecamp.guideY))

    }

    // 남은 미사일 수가 0이 되며 게임이 종료된다.
    private fun gameFinish() {
        Toast.makeText(this, "게임 종료! ", Toast.LENGTH_SHORT).show()
        binding.textGameover.isVisible = true
    }

    /**
     * Draw와 관련된 함수들
     * Model에서 값 가져와 화면에 그려줌
     */
    // 산(지형)을 Canvas에 그린다.
    private fun drawMountain(canvas: Canvas, vertex:MutableList<ArrayList<Float>>) {
        val paint = Paint()
        paint.strokeWidth = 5f
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
        paint.pathEffect = CornerPathEffect(100f)

        val landformPath = Path()
        landformPath.reset()

        // 맨 처음 그리기 시작할 점으로 이동
        landformPath.moveTo(0f, GameConfig.SCREEN_HEIGHT / 2)

        // 모델에서 꼭짓점 리스트를 가져와 반복문을 통해 돌면서 선으로 그린다.
        for (i in 0 until vertex.size) {
            landformPath.lineTo(vertex[i][0], vertex[i][1])
        }

        // 지형을 꽉 채워 색칠하기 위해 처음 위치까지 이동한다.
        landformPath.lineTo(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT)
        landformPath.lineTo(0f, GameConfig.SCREEN_HEIGHT)

        // Canvas에 실제로 그린다.
        canvas.drawPath(landformPath, paint)

    }

    // 베이스캠프 Canvas에 그린다.
    private fun drawBasecamp(canvas: Canvas, basecamp: Basecamp) {
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE

        // 모델에서 탱크 색상을 가져와 해당 색상으로 그린다.
        // basecamp 좌표는 중심점이기 때문에 탱크 사이즈 만큼 rect 크기를 잡아 그린다.
        paint.color = basecamp.color
        canvas.drawRect(
            (basecamp.x - GameConfig.TANK_SIZE),
            (basecamp.y - GameConfig.TANK_SIZE),
            (basecamp.x + GameConfig.TANK_SIZE),
            (basecamp.y + GameConfig.TANK_SIZE),
            paint
        )
    }

    // 적 탱크 리스트를 모델에서 가져와 Canvas에 그린다.
    private fun drawEnemies(canvas: Canvas, enemies: ArrayList<Tank>) {
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE

        // 여러 대의 적 탱크가 있기에 리스트를 받아 반복문을 돌며 그려준다.
        enemies.forEach {
            // 모델에서 탱크 색상을 가져와 해당 색상으로 그린다.
            // 탱크 좌표는 탱크의 중심점이기 때문에 탱크 사이즈 만큼 rect 크기를 잡아 그린다.
            paint.color = it.color
            canvas.drawRect(
                (it.x - GameConfig.TANK_SIZE),
                (it.y - GameConfig.TANK_SIZE),
                (it.x + GameConfig.TANK_SIZE),
                (it.y + GameConfig.TANK_SIZE),
                paint
            )
        }
    }

    // 미사일을 Canvas에 그린다
    private fun drawMissile(canvas: Canvas, missile: Missile) {

        val paint = Paint()
        paint.color = Color.BLACK

        // 모델에서 미사일 좌표를 가져온다.
        // 미사일 좌표는 미사일의 중심점이기 때문에 미사일 사이즈 만큼 rect 크기를 잡아 원을 그린다.
        canvas.drawOval(
            ((missile.x - GameConfig.MISSILE_SIZE).toFloat()),
            (missile.y.toFloat() - GameConfig.MISSILE_SIZE),
            ((missile.x + GameConfig.MISSILE_SIZE).toFloat()),
            ((missile.y + GameConfig.MISSILE_SIZE).toFloat()),
            paint
        )
    }

    // 미사일의 각도를 그려줄 가이르 라인을 Canvas에 그린다.
    private fun drawGuide(canvas: Canvas, basecamp: Basecamp, guidePoint: Array<Double>) {
        val paint = Paint()
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE
        paint.color = Color.DKGRAY

        val guideLinePath = Path()
        guideLinePath.reset()

        // 모델에서 Basecamp 중심 좌표를 가져와서 가이드 중심 좌표만큼 선을 그린다.
        guideLinePath.moveTo(basecamp.x, basecamp.y)
        guideLinePath.lineTo(guidePoint[0].toFloat(), guidePoint[1].toFloat())
        // Canvas에 실제로 그린다.
        canvas.drawPath(guideLinePath, paint)

    }
}