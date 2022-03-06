package com.example.projetcour

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projetcour.DATABASE_NAME
import com.example.projetcour.DataManager
import com.example.projetcour.DataManager.listeNotes
import com.example.projetcour.R
import com.example.projetcour.database.AppDatabase
import com.example.projetcour.database.notes.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrincipalActivity : AppCompatActivity() {
    private lateinit var monRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)


        listeNotes= mutableListOf()
       // initObservers()
        recupererNotes()
        monRecyclerView = findViewById(R.id.mon_recycler)
        monRecyclerView.adapter = CustomAdapter(listeNotes)
    }

    override fun onStart() {
        super.onStart()
        var activity=findViewById<Button>(R.id.bouton_nouveau)
        activity.setOnClickListener {
            var intent=Intent(this,NewNoteActivity::class.java)
            startActivity(intent)
        }
    }

    //bd
    val notesChanges: MutableLiveData<MutableList<Notes>> by lazy {
        MutableLiveData<MutableList<Notes>>()
    }


    private fun initObservers() {
        notesChanges.observe(
            this
        ) {
            findViewById<Button>(R.id.bouton_database).text = it.toString()
            listeNotes.clear()
            listeNotes.addAll(notesChanges.value!!)
        }
    }



    private fun recupererNotes() {
    listeNotes.clear()
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().getAll().forEach {

                Log.d("Suivi", "le titre est " +it.titre + " a le message +" + it.contenu+ " et a une cat "+it.categorie)
                listeNotes.add(it)

            }
            notesChanges.postValue(listeNotes)
        }
    }



    private fun saveNewNote() {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().insertAll(
                Notes(
                    findViewById<EditText>(R.id.titre).text.toString(),
                    findViewById<EditText>(R.id.contenu).text.toString(),
                    findViewById<EditText>(R.id.categorie).text.toString()
                )
            )
        }
    }
    private fun deleteNote(notes: Notes) {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().delete(
                Notes(
                    findViewById<EditText>(R.id.titre).text.toString(),
                    findViewById<EditText>(R.id.contenu).text.toString(),
                    findViewById<EditText>(R.id.categorie).text.toString()
                )
            )
        }
    }
}

class CustomAdapter(private val dataSet: List<Notes>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView2: TextView

        init {
            textView = view.findViewById(R.id.titre)
            textView2=view.findViewById(R.id.contenu)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.note, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].titre
        viewHolder.textView2.text = dataSet[position].contenu

    }

    override fun getItemCount() = dataSet.size


}