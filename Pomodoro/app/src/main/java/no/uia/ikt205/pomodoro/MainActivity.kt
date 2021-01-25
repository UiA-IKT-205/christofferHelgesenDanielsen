package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    var isRunning: Boolean = false;

    lateinit var timer: CountDownTimer
    lateinit var startButton: Button
    lateinit var Button_30: Button
    lateinit var Button_60: Button
    lateinit var Button_90: Button
    lateinit var Button_120: Button
    lateinit var coutdownDisplay: TextView

    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Button_30 = findViewById<Button>(R.id.countdown30)
        Button_30.setOnClickListener() {
            updateCountDownDisplay(1800000)
            val timerupdate = 1800000L
            timeToCountDownInMs = timerupdate

        }
        Button_60 = findViewById<Button>(R.id.countdown60)
        Button_60.setOnClickListener() {
            updateCountDownDisplay(3600000)
            val timerupdate = 3600000L
            timeToCountDownInMs = timerupdate

        }

        Button_90 = findViewById<Button>(R.id.countdown90)
        Button_90.setOnClickListener() {
            updateCountDownDisplay(5400000)
            val timerupdate = 5400000L
            timeToCountDownInMs = timerupdate

        }

        Button_120 = findViewById<Button>(R.id.countdown120)
        Button_120.setOnClickListener() {
            updateCountDownDisplay(7200000)
            val timerupdate = 7200000L
            timeToCountDownInMs = timerupdate


        }

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener() {
            if (isRunning) {
                resetTimer()
            } else {
                startCountDown(it)
            }
        }

        coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View) {

        timer = object : CountDownTimer(timeToCountDownInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        isRunning = true
        timer.start()

    }


    fun updateCountDownDisplay(timeInMs: Long) {
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

    private fun resetTimer() {
        timeToCountDownInMs = 0L
    }
}