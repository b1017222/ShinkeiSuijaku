package com.example.shinkeisuijaku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        restartButton.setOnClickListener{
            val intent = Intent(application,StartActivity::class.java)
            startActivity(intent)
        }
    }
}
