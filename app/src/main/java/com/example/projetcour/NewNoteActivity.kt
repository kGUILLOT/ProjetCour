package com.example.projetcour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.projetcour.database.AppDatabase
import com.example.projetcour.database.notes.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random



class NewNoteActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajout_note)
       // initDatabase()
        initObservers()

    }

    override fun onStart() {
        super.onStart()
        findViewById<Button>(R.id.bouton_database).setOnClickListener {
            recupererNotes()
            saveNewNote()
            Toast.makeText(this, "la note est enregistré", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }   //bd
    val notesChanges: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    private fun initObservers() {
        notesChanges.observe(
            this
        ) {
            findViewById<Button>(R.id.bouton_database).text = it
        }
    }


    private fun recupererNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().getAll().forEach {
                Log.d("Suivi", "le titre est " +it.titre + " a le message +" + it.contenu+ " et a une cat "+it.categorie)
                notesChanges.postValue("titre " + it.titre)
            }
        }
    }



    private fun saveNewNote() {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().insertAll(
                Notes(
                    findViewById<EditText>(R.id.titre).text.toString(),
                    findViewById<EditText>(R.id.contenu).getText().toString(),
                    findViewById<EditText>(R.id.categorie).getText().toString()
                )
            )
        }
    }
    private fun deleteNote(titre:String) {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().delete(
                Notes(
                    findViewById<EditText>(R.id.titre).text.toString(),
                    findViewById<EditText>(R.id.contenu).getText().toString(),
                    findViewById<EditText>(R.id.categorie).getText().toString()
                )
            )
        }
    }


}