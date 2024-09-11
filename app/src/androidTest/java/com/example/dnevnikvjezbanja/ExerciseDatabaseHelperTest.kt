package com.example.dnevnikvjezbanja

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExerciseDatabaseHelperTest {

    private lateinit var dbHelper: ExerciseDatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = ExerciseDatabaseHelper(context)
    }

    @After
    fun tearDown() {
        dbHelper.close()
    }

    @Test
    fun testInsertExercise() {
        val exercise = Exercise(0, "Trčanje", 30, 70.5f)
        dbHelper.insertExercise(exercise.name, exercise.duration, exercise.weight)

        val exercises = dbHelper.getAllExercises()
        assertTrue(exercises.isNotEmpty())
        assertEquals(exercise.name, exercises.last().name)
    }

    @Test
    fun testDeleteExercise() {
        val exercise = Exercise(0, "Čučnjevi", 15, 60f)
        dbHelper.insertExercise(exercise.name, exercise.duration, exercise.weight)

        val allExercises = dbHelper.getAllExercises()
        val lastInserted = allExercises.last()
        dbHelper.deleteExercise(lastInserted.id)

        val updatedExercises = dbHelper.getAllExercises()
        assertFalse(updatedExercises.any { it.name == "Čučnjevi" })
    }
}
