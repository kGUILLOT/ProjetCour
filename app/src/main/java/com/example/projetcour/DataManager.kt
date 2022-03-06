package com.example.projetcour

import com.example.projetcour.database.AppDatabase
import com.example.projetcour.database.notes.Notes


object DataManager {
    lateinit var db : AppDatabase
    var avatar: Int? = null
    var connexion: Int? = null
    var mood : String? = null
    lateinit var listeNotes : MutableList<Notes>



}