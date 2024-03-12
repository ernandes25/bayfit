package com.baysoftware.bayfit

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class UserManager private  constructor() {

    private val Context.dataTimer: DataStore<Preferences> by preferencesDataStore("settings")

    companion object {
        private val MINUTE_KEY = intPreferencesKey("TIME_MINUTE")
        val SECOND_KEY = intPreferencesKey("TIME_SECOND") //11/03/2024-transformei em public

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

    suspend fun readDataUser(context: Context ): User {
        val prefs = context.dataTimer.data.first()
        return User(
            minute = prefs[MINUTE_KEY] ?: 0,
            second = prefs[SECOND_KEY] ?: 0
        )
    }
}