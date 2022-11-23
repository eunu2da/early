package com.chobo.early

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.chobo.practice.R
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    //참가자수
    var p_num = 3
    //참가자번호
    var k = 1
    //point list 라는 리스트를 초기로 , 빈상태로 선언하고, 메인이계속반복이되면서 포인트리스트에 결정된 점수가 쌓일꺼다.
    val point_list = mutableListOf<Float>()

    fun start() {
        setContentView(R.layout.activity_start)
        val tv_pnum: TextView = findViewById(R.id.tv_pnum)
        val bm: Button = findViewById(R.id.btn_minus)
        val bp: Button = findViewById(R.id.btn_plus)
        val bs: Button = findViewById(R.id.btn_start)

        tv_pnum.text = p_num.toString()

        bm.setOnClickListener {
            p_num--
            if (p_num == 0) {
                p_num = 1
            }
            tv_pnum.text = p_num.toString()
        }

        bp.setOnClickListener {
            p_num++
            tv_pnum.text = p_num.toString()

        }
        bs.setOnClickListener {
            main()
        }
    }
    fun main(){
       setContentView(R.layout.activity_main)
        var timerTask: Timer? = null
        var stage = 1
        var sec: Int = 0
        val tv: TextView = findViewById(R.id.tv_random)
        val tvt: TextView = findViewById(R.id.tv_pnum)
        val tvp: TextView = findViewById(R.id.tv_point)
        val btn: Button = findViewById(R.id.btn_minus)
        val tv_people: TextView = findViewById(R.id.tv_people)

        val random_box = Random()
        val num = random_box.nextInt(1001)

        tv.text = ((num.toFloat())/100).toString();
        btn.text = "시작"
        tv_people.text ="참가자 $k"

        btn.setOnClickListener {
        stage ++

            if (stage == 2){
                timerTask = kotlin.concurrent.timer(period = 10) {
                    sec++
                    runOnUiThread {
                        tvt.text = (sec.toFloat()/100).toString()
                    }
                }
            btn.text = "정지"

            }else if (stage == 3) {
                timerTask?.cancel()
                val point = abs(sec - num).toFloat() / 100

                point_list.add(point)
                tvp.text = point.toString()
                btn.text = "다음"
                stage = 0

            }else if(stage == 1){
               if(k < p_num) {
                   k++
                   main()
               }else {
                   end()
               }
               }

            }
        }

    fun end(){
        setContentView(R.layout.activity_finish)
        val textView2: TextView = findViewById(R.id.textView2)
        val tv_lose: TextView = findViewById(R.id.tv_lose)
        val replay: Button = findViewById(R.id.replay)

        tv_lose.text = (point_list.maxOrNull().toString())
        var index_last = point_list.indexOf(point_list.maxOrNull())
        textView2.text = "참가자" + (index_last).toString()

        replay.setOnClickListener {

            point_list.clear()
            k = 1
            start()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()

    }

}




