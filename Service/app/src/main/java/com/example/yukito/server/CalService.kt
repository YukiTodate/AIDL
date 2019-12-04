package com.example.yukito.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import com.example.yukito.IMyAidlInterface

class CalService:Service(){
    private val binder : IMyAidlInterface.Stub=object : IMyAidlInterface.Stub(){
        override fun getMessage(name: String?): String {
            return "hello $name"
        }
    }
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
}