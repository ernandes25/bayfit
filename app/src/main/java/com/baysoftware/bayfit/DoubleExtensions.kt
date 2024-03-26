package com.baysoftware.bayfit

import kotlin.math.roundToInt

const val TOTAL_MINUTES_IN_DAY = 86400
const val TOTAL_MINUTES_IN_HOUR = 3600
const val TOTAL_HOURS = 60

fun Double.getTimeStringFromDouble(): String {
    val resultInt = this.roundToInt()
    val hours = resultInt % TOTAL_MINUTES_IN_DAY / TOTAL_MINUTES_IN_HOUR
    val minutes = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR / TOTAL_HOURS
    val seconds = resultInt % TOTAL_MINUTES_IN_DAY % TOTAL_MINUTES_IN_HOUR % TOTAL_HOURS
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}