package com.baysoftware.bayfit

import android.content.Intent

class DecreasingTimerService : TimerService() {

    override fun getTimeValue(time: Double): Double = time - 1

    override fun getTimerIntent(): Intent = Intent(TIMER_UPDATE)

    companion object {
        const val TIMER_UPDATE = "decreasingTimerUpdate"
    }

}