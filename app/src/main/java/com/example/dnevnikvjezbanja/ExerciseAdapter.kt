package com.example.dnevnikvjezbanja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val onItemLongClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textViewExerciseName)
        val durationTextView: TextView = view.findViewById(R.id.textViewDuration)
        val weightTextView: TextView = view.findViewById(R.id.textViewWeight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.nameTextView.text = exercise.name
        holder.durationTextView.text = "Trajanje: ${exercise.duration} min"
        holder.weightTextView.text = "Težina: ${exercise.weight} kg"

        holder.itemView.setOnLongClickListener {
            onItemLongClick(exercise)
            true
        }
    }

    override fun getItemCount() = exercises.size
}
