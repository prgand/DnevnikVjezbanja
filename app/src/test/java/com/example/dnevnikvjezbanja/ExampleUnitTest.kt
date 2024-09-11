package com.example.dnevnikvjezbanja

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExerciseAdapterTest {

    private lateinit var adapter: ExerciseAdapter
    private lateinit var exercises: List<Exercise>

    @Before
    fun setUp() {
        exercises = listOf(
            Exercise(1, "Trčanje", 30, 70.5f),
            Exercise(2, "Plivanje", 60, 65.0f)
        )
        adapter = ExerciseAdapter(exercises) { exercise ->
        }
    }

    @Test
    fun testItemCount() {
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun testGetItem() {
        val exercise = exercises[0]
        assertEquals("Trčanje", exercise.name)
        assertEquals(30, exercise.duration)
    }
}
