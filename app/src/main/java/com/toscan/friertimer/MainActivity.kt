package com.toscan.friertimer

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.toscan.friertimer.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timer1: CountDownTimer
    private lateinit var timer2: CountDownTimer
    private var totalRunTime = 10000L * 1000L
    private var cookingTime = 7


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar1.max = 60 * cookingTime
        binding.progressBar2.max = 60 * cookingTime

        this.timer1 = object: CountDownTimer(totalRunTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(binding.frier1, binding.progressBar1, millisUntilFinished)
            }
            override fun onFinish() { }
        }

        this.timer2 = object: CountDownTimer(totalRunTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(binding.frier2, binding.progressBar2, millisUntilFinished)
            }
            override fun onFinish() { }
        }

        binding.frier1.setOnLongClickListener {
            stop(binding.frier1, timer1)
            true
        }

        binding.frier2.setOnLongClickListener {
            stop(binding.frier2, timer2)
            true
        }
    }


    fun onClickFrier1(view: View) {
        start(binding.frier1, timer1)
    }

    fun onClickFrier2(view: View) {
        start(binding.frier2, timer2)
    }

    private fun start(btn: ExtendedFloatingActionButton, timer: CountDownTimer) {
        btn.setBackgroundColor(Color.rgb(0, 0xbf, 0xa5))
        timer.cancel()
        timer.start()
    }

    private fun stop(btn: ExtendedFloatingActionButton, timer: CountDownTimer) {
        btn.setBackgroundColor(Color.rgb(0x30, 0x3f, 0x9f))
        btn.text = "START"
        timer.cancel()
    }

    private fun onTick(btn: ExtendedFloatingActionButton, bar: ProgressBar, timeLeft: Long) {
        val min = ((totalRunTime - timeLeft) / 1000) / 60
        val sec = ((totalRunTime - timeLeft) / 1000) % 60
        btn.text = "$min:${sec.toString().padStart(2, '0')}"

        val pr = ((totalRunTime - timeLeft) / 1000).toInt()
        bar.progress = pr

        if (pr > (60 * cookingTime))
            btn.setBackgroundColor(Color.RED)
        else if (pr > (60 * (cookingTime - 2)))
            btn.setBackgroundColor(Color.rgb(0xf5, 0x7f, 0x17))

    }
}