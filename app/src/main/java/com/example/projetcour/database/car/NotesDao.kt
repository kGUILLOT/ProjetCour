package com.example.myapplication.database.car

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<Notes>

    @Insert
    fun insertAll(vararg notes: Notes)

    @Delete
    fun delete(notes: Notes)
}