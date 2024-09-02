package com.example.connect.log

import android.util.Log

object MyLog {
    private val TAG = "MyLog"
    fun d(tag: String, message: String) {
        Log.d(TAG, "$tag $message")
    }

    fun e(tag: String,message: String) {
        Log.e(TAG, "$tag $message")
    }

    fun i(tag: String,message: String) {
        Log.i(TAG, "$tag $message")
    }

}