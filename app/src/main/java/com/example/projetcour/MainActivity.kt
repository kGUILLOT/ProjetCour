package com.example.projetcour

import android.content.Context
import android.content.Intent
import android.os.Bundle
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


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        loadSharedPrefs()
        initDatabase()
        initObservers()
        var condition =findViewById<CheckBox>(R.id.checkBox)
        if (DataManager.connexion==0){
            var activity=findViewById<Button>(R.id.button)
            activity.setOnClickListener {
                if (findViewById<CheckBox>(R.id.checkBox).isChecked) {
                    var intent = Intent(this, AgendaActivity::class.java)

                    enregistrerConnexion()
                    enregistrerImage()
                    Log.d("Suivi","mon img est "+DataManager.avatar)
                    Log.d("Suivi","ma co est "+DataManager.connexion)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Veuillez accepter les conditions", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }else{
            if (DataManager.avatar==0) {
                findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.man)
            }
            if (DataManager.avatar==1) {
                findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.man2)
            }
            if (DataManager.avatar==2) {
                findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.woman)
            }
            Log.d("Suivi","mon img est "+DataManager.avatar)
            Log.d("Suivi","ma co est "+DataManager.connexion)
            var activity=findViewById<Button>(R.id.button)
            activity.setOnClickListener{
                var intent = Intent(this, AgendaActivity::class.java)
                startActivity(intent)
            }
            findViewById<TextView>(R.id.texteCoach).setText("Te voila de retour")
            condition.isVisible = false
            condition.isChecked = true
        }
    }
    private fun enregistrerImage(){
         val number= listOf(0,1,2)
        val image=number.shuffled().last()
        Log.d("Suivi","L'imagen genere est "+image)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("avatar", image)
            apply()
        }

    }
    private fun enregistrerConnexion() {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("connexion", 1)
            apply()
        }
    }

    private fun loadSharedPrefs(){
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        DataManager.connexion = sharedPref.getInt("connexion", 0)
        DataManager.avatar = sharedPref.getInt("avatar", 0)
    }
    //bd

    val notesChanges: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    private fun recupererNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().getAll().forEach {
                Log.d("Suivi", it.titre + " a une couleur" + it.contenu)
                notesChanges.postValue("titre " + it.titre)
            }
        }
    }

    private fun initObservers() {
        notesChanges.observe(
            this
        ) {
            findViewById<Button>(R.id.bouton_database).text = it
        }
    }

    private fun initDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }


    private fun saveNewUser() {
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.db.noteDao().insertAll(
                Notes(
                    findViewById<EditText>(R.id.model).text.toString(),

                    findViewById<EditText>(R.id.couleur).getText().toString()
                )
            )
        }
    }
}