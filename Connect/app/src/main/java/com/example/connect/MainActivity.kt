package com.example.connect

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
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


class MainActivity : ComponentActivity() {
    private val mTag = "Home"
    private val adapter = BluetoothAdapter.getDefaultAdapter()
   private var mListView : ListView? = null
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

        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(mReceiver, intentFilter)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast("Permission needed to scan bluetooth devices")
            return
        }
        adapter.startDiscovery()
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(mTag, intent?.action.toString())
            when (intent?.action) {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    showToast(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    showToast(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
                }

                BluetoothDevice.ACTION_FOUND -> {
                    showToast(BluetoothDevice.ACTION_FOUND)
                    showDevices(context)

                }

                else -> "UnKnown"
            }
        }

    }

    private fun showDevices(context: Context?) {
        if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) {
            val device =
                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as BluetoothDevice?
            if (context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.BLUETOOTH_CONNECT
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                showToast("Permission needed to show bluetooth devices")
                return
            }
            val mAdapter = ArrayAdapter<String>(this, R.layout.devices_list)
            mListView?.setAdapter(mAdapter)
            showToast("Found device " + device?.getName())
        }
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }
}