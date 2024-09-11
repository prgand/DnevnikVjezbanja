package com.example.dnevnikvjezbanja

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExerciseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "exercise.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "exercises"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_WEIGHT = "weight"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, $COLUMN_DURATION INTEGER, $COLUMN_WEIGHT REAL)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExercise(name: String, duration: Int, weight: Float) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DURATION, duration)
            put(COLUMN_WEIGHT, weight)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllExercises(): List<Exercise> {
        val exercises = mutableListOf<Exercise>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val duration = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION))
                val weight = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT))

                exercises.add(Exercise(id, name, duration, weight))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return exercises
    }

    fun deleteExercise(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun updateExercise(id: Int, name: String, duration: Int, weight: Float) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DURATION, duration)
            put(COLUMN_WEIGHT, weight)
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

}
