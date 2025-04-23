package com.baysoftware.bayfit.history.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.history.model.ExerciseSessionModel
import com.baysoftware.bayfit.util.getTimeStringFromDouble
import com.baysoftware.bayfit.util.getTimeStringFromLong

class ExerciseSessionAdapter(
    private val onClick: (ExerciseSessionModel) -> Unit
) : RecyclerView.Adapter<ExerciseSessionAdapter.ExerciseViewHolder>() {

    private lateinit var sessions: List<ExerciseSessionModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_session, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(sessions[position])
        holder.itemView.setOnClickListener { onClick(sessions[position]) }
    }

    override fun getItemCount(): Int = sessions.size

    fun updateSessions(newSessions: List<ExerciseSessionModel>) {
        sessions = newSessions
        notifyDataSetChanged()
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.date_item)
        private val durationTextView: TextView = itemView.findViewById(R.id.duration)

        fun bind(session: ExerciseSessionModel) {
            dateTextView.text = session.date.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy"))
            durationTextView.text = session.duration.getTimeStringFromLong()
        }
    }
}