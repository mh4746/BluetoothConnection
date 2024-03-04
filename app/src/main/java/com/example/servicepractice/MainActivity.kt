package com.example.servicepractice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.servicepractice.service.MyService


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart = findViewById<Button>(R.id.btn_start)
        val btnStop = findViewById<Button>(R.id.btn_stop)
        val btnNext = findViewById<Button>(R.id.btn_next)
        val text = findViewById<TextView>(R.id.tv_text)

        btnStart.setOnClickListener {
            startService(Intent(this, MyService::class.java))
        }
        btnStop.setOnClickListener {
           stopService(Intent(this, MyService::class.java))
        }
        btnNext.setOnClickListener {

        }
    }
}