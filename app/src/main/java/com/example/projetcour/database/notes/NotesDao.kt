package com.example.projetcour.database.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<Notes>
    @Query("SELECT * FROM notes WHERE titre=:titre")
    fun getNoteByID(titre: String): Notes

    @Query("DELETE FROM notes WHERE titre=:titre")
    fun deleteByUserId(titre: String)

    @Insert
    fun insertAll(vararg notes: Notes)

    @Delete
    fun delete(notes: Notes)

}