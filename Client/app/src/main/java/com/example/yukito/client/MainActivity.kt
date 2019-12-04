package com.example.yukito.client

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity
import com.example.yukito.IMyAidlInterface
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var calService: IMyAidlInterface? = null

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            calService = IMyAidlInterface.Stub.asInterface(service)
            Toast.makeText(applicationContext, "Service Connected", Toast.LENGTH_SHORT).show()
        }
        override fun onServiceDisconnected(name: ComponentName) {
            calService = null
            Toast.makeText(applicationContext, "Service Disconnected", Toast.LENGTH_SHORT)
                .show()
        }
    }
    override fun onStart() {
        super.onStart()
        if (calService == null) {
            val it = Intent("myservice")
            it.setPackage("com.example.yukito.server")
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            try {
                val message = calService?.getMessage("yuki")
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            } catch (e: RemoteException) {
                e.printStackTrace()
                Log.d("client", e.toString())
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
}