package com.baysoftware.bayfit

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.Timer
import java.util.TimerTask

abstract class TimerService : Service() {

    abstract fun getTimeValue(time: Double): Double

    abstract fun getTimerIntent(): Intent

    override fun onBind(p0: Intent): IBinder? = null

    private val timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startID: Int): Int {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask() {
        override fun run() {
            val intent = getTimerIntent()
            time = getTimeValue(time)
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }

    companion object {
        const val TIME_EXTRA = "timeExtra"
    }
}