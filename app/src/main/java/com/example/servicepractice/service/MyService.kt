package com.example.servicepractice.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.servicepractice.R

open class MyService : Service() {
    lateinit var myPlayer : MediaPlayer
    val TAG  = "MyService"
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        myPlayer = MediaPlayer()
        Log.d(TAG,"Service Created")
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show()
        myPlayer.isLooping = false
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        myPlayer.start()
        Log.d(TAG,"Service  onStartCommand")
        Toast.makeText(this, "Service  onStartCommand", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"Service  onDestroy")
        Toast.makeText(this, "Service  onDestroy", Toast.LENGTH_LONG).show();
        myPlayer.stop()
    }

}