package com.example.dnevnikvjezbanja

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private lateinit var exerciseDatabaseHelper: ExerciseDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewExercises)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        exerciseDatabaseHelper = ExerciseDatabaseHelper(requireContext())
        val exercises = getExercisesFromDatabase()

        // Poveži adapter s RecyclerView-om i dodaj long click listener
        adapter = ExerciseAdapter(exercises) { exercise ->
            showOptionsDialog(exercise)
        }
        recyclerView.adapter = adapter

        return view
    }

    private fun getExercisesFromDatabase(): List<Exercise> {
        return exerciseDatabaseHelper.getAllExercises()
    }

    // Prikaži dijalog s opcijama za brisanje i ažuriranje
    private fun showOptionsDialog(exercise: Exercise) {
        val options = arrayOf("Obriši", "Uredi")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Odaberite opciju")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> deleteExercise(exercise) // Opcija "Obriši"
                1 -> updateExercise(exercise) // Opcija "Uredi"
            }
        }
        builder.show()
    }

    // Funkcija za brisanje vježbi
    private fun deleteExercise(exercise: Exercise) {
        exerciseDatabaseHelper.deleteExercise(exercise.id)
        refreshRecyclerView() // Nakon brisanja, osvježi RecyclerView
    }

    // Funkcija za uređivanje vježbi (za sada ćemo samo prikaži poruku)
    private fun updateExercise(exercise: Exercise) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ažuriraj vježbu")

        // Prikaz forme za ažuriranje unutar dijaloga
        val view = layoutInflater.inflate(R.layout.dialog_update_exercise, null)
        val exerciseNameInput = view.findViewById<EditText>(R.id.editTextExerciseName)
        val durationInput = view.findViewById<EditText>(R.id.editTextDuration)
        val weightInput = view.findViewById<EditText>(R.id.editTextWeight)

        exerciseNameInput.setText(exercise.name)
        durationInput.setText(exercise.duration.toString())
        weightInput.setText(exercise.weight.toString())

        builder.setView(view)
        builder.setPositiveButton("Spremi") { dialog, _ ->
            val updatedName = exerciseNameInput.text.toString()
            val updatedDuration = durationInput.text.toString().toInt()
            val updatedWeight = weightInput.text.toString().toFloat()

            // Ažuriraj vježbu u bazi podataka
            exerciseDatabaseHelper.updateExercise(exercise.id, updatedName, updatedDuration, updatedWeight)
            refreshRecyclerView()
            dialog.dismiss()
        }
        builder.setNegativeButton("Otkaži") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    // Osvježi RecyclerView nakon brisanja
    private fun refreshRecyclerView() {
        val exercises = getExercisesFromDatabase()
        adapter = ExerciseAdapter(exercises) { exercise ->
            showOptionsDialog(exercise)
        }
        recyclerView.adapter = adapter
    }


}

