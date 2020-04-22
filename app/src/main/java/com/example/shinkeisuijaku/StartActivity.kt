package com.example.shinkeisuijaku

import android.content.Intent
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start.*
import com.example.shinkeisuijaku.R.raw.*

class StartActivity : AppCompatActivity() {
    private val start = R.raw.start
    lateinit var soundPool: SoundPool


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        soundPool= SoundPool.Builder()
            .setMaxStreams(1)
            .build()
        var start = soundPool.load(this,start,1)



        //スタートボタンを押すとPlayActivityに遷移する
        startButton.setOnClickListener{
            soundPool.play(start, 1.0f, 1.0f, 1, 0, 1.0f)
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)

        }

    }
}


