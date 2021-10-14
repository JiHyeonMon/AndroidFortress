package com.example.example.androidfortress.model

class Mountain {

    // 기기의 화면 너비 width, height 지정
    // 해당 가로 세로 안에서 랜덤으로 지형 생성
    private val width = GameConfig.SCREEN_WIDTH
    private val height = GameConfig.SCREEN_HEIGHT

    // 지형을 그릴 때, 꼭짓점을 만들고 해당 꼭짓점을 연결하여 산을 그릴 것
    // 꼭지점을 담을 리스트 선언
    // 제일 첫 지형의 시작점 넣어준다.
    var vertex: MutableList<ArrayList<Float>> = mutableListOf(arrayListOf(0f, height / 2))


    /**
     * Public Method
     */

    // 산(지형) 초기화
    // 산을 그릴 꼭지점 랜덤하게 생성
    fun initMountain() {
        // 몇개의 꼭지점을 갖는 지형을 그릴지, 랜덤하게 개수를 뽑는다.
        val vertexNum = (4..6).random()

        for (i in 0 until vertexNum) {
            // 뽑을 꼭짓점 개수만큼 반복문을 돌며 x, y 값을 생성한다.

            // 가로너비에서 균등하게 나눠서 x좌표를 만든다.
            val x = (i + 1) * this.width / vertexNum

            // y는 번갈아가며 높은 곳의 위치, 낮은 곳의 위치를 뽑아 지그재그로 지형이 울퉁불퉁하게 표현될 수 있는 선에서 랜덤하게 뽑는다.
            val y = if (i % 2 == 0) {
                // 화면의 상단에 위치할 수 있게 화면 세로비의 0.1~0.5에 해당하는 구간 사이에 위치할 수 있도록 한다.
                ((this.height * 0.1).toInt()..(this.height * 0.5).toInt()).random().toFloat()
            } else {
                // 화면의 하단에 위치할 수 있게 화면 세로비의 0.6~0.75에 해당하는 구간 사이에 위치할 수 있도록 한다.
                ((this.height * 0.6).toInt()..(this.height * 0.75).toInt()).random().toFloat()
            }

            // 생성한 x,y 좌표를 리스트로 넣고 꼭짓점 리스트에 넣는다.
            vertex.add(arrayListOf(x, y))
        }
    }

    // 탱크의 위치를 설정할 때, X 좌표를 입력으로 주면 해당 X위치의 산의 Y 값을 반환하여 해당 X,Y 위치에 탱크가 위치할 수 있게 한다.
    fun getVertexY(x: Float): Float {
        // 탱크의 위치가 산(지형)의 위에 존재한다.
        // --> 탱크 위치를 지정할 때, 산의 좌표가 필요하다.
        // 그러나 산은 꼭짓점을 이어 선분을 그린 것이기에 특정 x 좌표에 대한 y를 구할 때, 이어그린 꼭지점의 직선의 방정식이 필요하다.

        // 직선의 방정식을 그리려면 두 점의 좌표가 필요하다.
        // 계산에 필요한 두 점의 좌표를 넣어줄 x1, y1, x2, y2 변수 생성
        var x1 = 0f
        var x2 = 0f
        var y1 = 0f
        var y2 = 0f


        // 꼭지점들을 담은 리스트를 반복문을 통해 돌면서, 입력으로 들어온 x가 어떤 두 꼭지점 사이의 x 값인지 찾는다.
        for (i in vertex.indices) {
            if (vertex[i][0] > x) {
                // 입력 x에 대한 두 꼭짓점을 찾아서 x1, y1, x2, y2 변수에 좌표를 넣어준다.
                x1 = vertex[i - 1][0]
                y1 = vertex[i - 1][1]
                x2 = vertex[i][0]
                y2 = vertex[i][1]
                break
            }
        }

        // 두 점을 지나는 직선의 방정식
        // 입력 x에 대한 y 값을 구해서 반환한다.
        return (y2 - y1) / (x2 - x1) * (x - x1) + y1
    }

}
