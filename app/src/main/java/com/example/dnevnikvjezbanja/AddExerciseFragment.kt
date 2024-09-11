package com.example.dnevnikvjezbanja

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AddExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_exercise, container, false)

        val exerciseNameInput = view.findViewById<EditText>(R.id.editTextExerciseName)
        val durationInput = view.findViewById<EditText>(R.id.editTextDuration)
        val weightInput = view.findViewById<EditText>(R.id.editTextWeight)
        val saveButton = view.findViewById<Button>(R.id.buttonSaveExercise)

        saveButton.setOnClickListener {
            val exerciseName = exerciseNameInput.text.toString()
            val duration = durationInput.text.toString().toIntOrNull()
            val weight = weightInput.text.toString().toFloatOrNull()

            if (exerciseName.isNotEmpty() && duration != null && weight != null) {
                val dbHelper = ExerciseDatabaseHelper(requireContext())
                dbHelper.insertExercise(exerciseName, duration, weight)
            }
        }

        return view
    }
}
