package no.uia.ikt205.pomodoro

import android.app.assist.AssistContent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime



class MainActivity : AppCompatActivity() {

    lateinit var pauseTimer: CountDownTimer
    lateinit var timer: CountDownTimer
    lateinit var startButton: Button
    lateinit var countdownDisplay: TextView
    lateinit var intervalInput: EditText


    var intervalAmount: Int = 0

    var timeToCountDownInMs = 5000L
    var PauseTimeToCountDownInMs = 5000L
    val timeTicks = 1000L

    var sliderCountDownTimerInMin: Long = 15
    var PauseSliderCountDownTimerInMin: Long = 15

    var running = false
    var pause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intervalInput = findViewById<EditText>(R.id.workIntervalChoice)

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener() {

            startCountDown(it)
            running = true

        }

        countdownDisplay = findViewById<TextView>(R.id.countDownView)


        val seek = findViewById<SeekBar>(R.id.countdownSlider)
        seek?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
         sliderCountDownTimerInMin = progress.toLong()
                timeToCountDownInMs = sliderCountDownTimerInMin * 60000L
                updateCountDownDisplay(timeToCountDownInMs)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@MainActivity,
                        "Time in min: " + seek.progress,
                        Toast.LENGTH_SHORT).show()
            }
        })

        val pauseSeekBar = findViewById<SeekBar>(R.id.pauseCountDownSlider)
        pauseSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(pauseSeekBar: SeekBar, progress: Int, fromUser: Boolean) {
                PauseSliderCountDownTimerInMin = progress.toLong()
                PauseTimeToCountDownInMs = PauseSliderCountDownTimerInMin * 60000L
                updateCountDownDisplay(PauseTimeToCountDownInMs)
            }

            override fun onStartTrackingTouch(pauseSeekBar: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(pauseSeekBar: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@MainActivity,
                        "Time in min: " + pauseSeekBar.progress,
                        Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun startCountDown(v: View) {

            timer = object : CountDownTimer(timeToCountDownInMs, timeTicks) {
                override fun onFinish() {

                    if (intervalAmount >= 1){
                        Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                        pause = true
                        startPauseCountDown(v)
                    } else {
                        Toast.makeText(this@MainActivity,"Alle økter ferdige", Toast.LENGTH_SHORT).show()
                        running = false
                    }
                }

                override fun onTick(millisUntilFinished: Long) {
                    updateCountDownDisplay(millisUntilFinished)
                }
            }

        if(!intervalInput.text.isEmpty()){
            intervalAmount = intervalInput.text.toString().toInt()
        }else{
            intervalAmount = 0
        }
            startButton.isEnabled = false
            timer.start()

    }

    fun startPauseCountDown(v: View) {
        pauseTimer = object : CountDownTimer(PauseTimeToCountDownInMs, timeTicks) {

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Pause er ferdig", Toast.LENGTH_SHORT).show()

                startButton.isEnabled = true

                if(intervalAmount >= 1){
                    intervalAmount -= 1
                    intervalInput.setText(intervalAmount.toString())

                    pause = false
                    startCountDown(v)
                }else{
                    running = false
                    startButton.isEnabled = true
                }

            }
            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        startButton.isEnabled = false
        pauseTimer.start()

    }

    fun updateCountDownDisplay(timeInMs: Long) {
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }




}