package com.baysoftware.bayfit.running.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.baysoftware.bayfit.db.ExerciseDatabase
import com.baysoftware.bayfit.db.ExerciseRepository
import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import com.baysoftware.bayfit.util.getTimeStringFromDouble
import kotlinx.coroutines.launch
import java.time.LocalDate

class RunningTimerViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    companion object {
        fun provideFactory(
            application: Application,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    val database = ExerciseDatabase.getDatabase(application)
                    val exerciseRepository = ExerciseRepository(database.exerciseSessionDAO())
                    return RunningTimerViewModel(exerciseRepository) as T
                }
            }
    }

    private var startTime: Long = 0L

    fun startSession() {
        startTime = System.currentTimeMillis()
        Log.d("RunningTimerViewModel", "Session started at $startTime")
    }

    fun saveSession(endTime: Long, totalRestTime: Long) {
        viewModelScope.launch {
            val duration = endTime - startTime
            Log.d("RunningTimerViewModel", "Session ended at $endTime")
            Log.d("RunningTimerViewModel", "Duration: ${(duration / 1000.0).getTimeStringFromDouble()}")
            Log.d("RunningTimerViewModel", "Total Rest Time: ${(totalRestTime / 1000.0).getTimeStringFromDouble()}")

            val exerciseSession = ExerciseSessionModel(
                date = LocalDate.now().toString(),
                duration = duration,
                startTime = startTime,
                endTime = endTime,
                restTime = totalRestTime
            )
            exerciseRepository.insertSession(exerciseSession)
        }
    }
}