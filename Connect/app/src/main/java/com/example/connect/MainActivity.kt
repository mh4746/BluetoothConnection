package com.example.connect

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import com.example.connect.constant.GlobalConstant
import com.example.connect.log.MyLog

class MainActivity : ComponentActivity() {
    private val mTag = "Home"
    private val adapter = BluetoothAdapter.getDefaultAdapter()
    private var mListView: ListView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mListView = findViewById(R.id.mList)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.scan_list)
        val tvList = findViewById<TextView>(R.id.tv_list)
        btn.setOnClickListener {
            scanCall()
            tvList.text = getString(R.string.scanning)
        }

    }

    private fun scanCall() {
        val btManager = getSystemServiceName(BluetoothManager::class.java)
        val btAdapter = BluetoothAdapter.getDefaultAdapter()
        if (btAdapter == null) {
            MyLog.d(mTag, "btAdapter is null")
            return
        }

        if (btAdapter.isEnabled) {
            MyLog.d(mTag, "Bluetooth is on")
        } else {
            MyLog.d(mTag, "Bluetooth is off")
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                MyLog.d(mTag, "Bluetooth permission is not granted")
                return
            }
            startActivityForResult(enableBtIntent, GlobalConstant.REQUEST_ENABLE_BT)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == GlobalConstant.REQUEST_ENABLE_BT) {
                MyLog.d(mTag, "Bluetooth enable allow")
            } else {
                MyLog.d(mTag, "Bluetooth enable deny")
            }
        } else {
            MyLog.d(mTag, "An error occurred")
        }

    }
}
