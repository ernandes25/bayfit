package com.baysoftware.bayfit

import android.content.Intent
import androidx.datastore.core.DataStore

class DecreasingTimerService : TimerService() {

    override fun getTimeValue(time: Double): Double = time - 1

    override fun getTimerIntent(): Intent = Intent(TIMER_UPDATE)

//    private fun decreasingTime(): DataStore = UserManager.SECOND_KEY //11/03/2024

    companion object {
        const val TIMER_UPDATE = "decreasingTimerUpdate"
    }
}