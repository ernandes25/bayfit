package com.baysoftware.bayfit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


class UserManager(val context: Context) {
    val Context.dataTimer: DataStore<Preferences> by preferencesDataStore("settings")

    companion object {
        private val MINUTE_KEY = intPreferencesKey("TIME_MINUTE")
        private val SECOND_KEY = intPreferencesKey("TIME_SECOND")

    }

     suspend fun saveDataUser(minute: Context, second: Context) {
        context.dataTimer.edit {
            it[MINUTE_KEY] = minute
            it[SECOND_KEY] = second
        }
    }

    suspend fun readDataUser(): User {
        val prefs = context.dataTimer.data.first()

        return User(
            minute = prefs[MINUTE_KEY] ?: "",
            second = prefs[SECOND_KEY] ?: 0)

    }
}
