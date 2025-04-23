package com.baysoftware.bayfit.util

import java.util.Locale

fun Double.getTimeStringFromDouble(): String {
    val totalSeconds = this.toLong() % 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
}

fun Long.getTimeStringFromLong(): String {

    val seconds = (this / 1000) % 60
    val minutes = (this /(1000 * 60) ) % 60
    val hours = (this /(1000 * 3600)) % 24

    return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
}