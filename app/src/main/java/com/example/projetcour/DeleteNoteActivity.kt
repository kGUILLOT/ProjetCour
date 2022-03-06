package com.example.projetcour

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.projetcour.DataManager
import com.example.projetcour.database.notes.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.suppresion)
        // initDatabase()
        initObservers()

    }

    override fun onStart() {
        super.onStart()
        findViewById<Button>(R.id.bouton_suppression).setOnClickListener {
            deleteNote(findViewById<EditText>(R.id.titre_suppression).text.toString())
            Toast.makeText(this, "la note est supprimé", Toast.LENGTH_SHORT).show()
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

    private fun recupererNotesTitre(titre :String): Notes {
        lateinit var note: Notes
        CoroutineScope(Dispatchers.IO).launch {
            note= DataManager.db.noteDao().getNoteByID(titre)

        }
        return note
    }
    private fun deleteNote(titre: String) {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().deleteByUserId(titre)
        }
    }
}