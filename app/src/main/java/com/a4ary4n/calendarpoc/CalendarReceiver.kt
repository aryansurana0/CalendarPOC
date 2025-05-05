package com.a4ary4n.calendarpoc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.gson.Gson

private const val TAG = "CalendarReceiver: "

class CalendarReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("$TAG $context | ${Gson().toJson(intent)}")
    }
}