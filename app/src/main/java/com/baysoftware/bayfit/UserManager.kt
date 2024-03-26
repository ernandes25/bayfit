package com.baysoftware.bayfit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class UserManager private constructor() {

    private val Context.dataTimer: DataStore<Preferences> by preferencesDataStore("settings")

    companion object {

        private val MINUTE_KEY = intPreferencesKey("TIME_MINUTE")
        private val SECOND_KEY = intPreferencesKey("TIME_SECOND")
        private val TIMER_MODE = stringPreferencesKey("TIMER_MODE")

        @Volatile
        private var instance: UserManager? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: UserManager().also { instance = it }
        }
    }

    suspend fun saveDataUser(context: Context, minute: Int, second: Int) {
        context.dataTimer.edit {
            it[MINUTE_KEY] = minute
            it[SECOND_KEY] = second
        }
    }

    suspend fun readTimerConfiguration(context: Context): TimerConfiguration {
        val prefs = context.dataTimer.data.first()
        return TimerConfiguration(
            minute = prefs[MINUTE_KEY] ?: 0,
            second = prefs[SECOND_KEY] ?: 0
        )
    }

    suspend fun saveTimerMode(context: Context, mode: TimerMode) {
        context.dataTimer.edit {
            it[TIMER_MODE] = mode.stringValue
        }
    }

    suspend fun readTimerMode(context: Context): TimerMode {
        val prefs = context.dataTimer.data.first()
        return prefs[TIMER_MODE]?.let { TimerMode.fromString(it) } ?: TimerMode.PREDEFINED
    }

     enum class TimerMode(val stringValue: String) {
        UNDEFINED("UNDEFINED"),
         PREDEFINED("PREDEFINED"),
        FREE("FREE");

        companion object {
            fun fromString(value: String) = entries.first { it.stringValue == value }
        }
    }
}