package com.example.projetcour.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projetcour.database.notes.Notes
import com.example.projetcour.database.notes.NotesDao


@Database(entities = [Notes::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NotesDao
}