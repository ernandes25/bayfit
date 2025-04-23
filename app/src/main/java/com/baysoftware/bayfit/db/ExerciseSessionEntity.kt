package com.baysoftware.bayfit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baysoftware.bayfit.history.model.ExerciseSessionModel

@Entity(tableName = "exercise_sessions")
data class ExerciseSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val duration: Long,
    var startTime: Long,
    var endTime: Long,
    var restTime: Long
)

fun ExerciseSessionEntity.toModel() =
    ExerciseSessionModel(
        id = this.id,
        date = this.date,
        duration = this.duration,
        startTime = this.startTime,
        endTime = this.endTime,
        restTime = this.restTime
    )

