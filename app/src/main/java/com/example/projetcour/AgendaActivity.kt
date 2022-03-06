package com.example.projetcour

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class AgendaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

    }

    override fun onStart() {
        super.onStart()
        loadSharedPrefs()

        findViewById<ImageButton>(R.id.postit).setOnClickListener {
            enregistrerMood("Memo")
            affichageEnregistrement("Memo")
            redirection()
        }
        findViewById<ImageButton>(R.id.bouton_calendrier).setOnClickListener {
            enregistrerMood("Organisateur")
            affichageEnregistrement("Organisateur")
            redirection()
        }
        findViewById<ImageButton>(R.id.bouton_pro).setOnClickListener {
            enregistrerMood("Professionnel")
            affichageEnregistrement("Professionnel")
            redirection()
        }
        findViewById<ImageButton>(R.id.bouton_amour).setOnClickListener {
            enregistrerMood("Intime")
            affichageEnregistrement("Intime")
            Log.d("Suivi","le mood est "+DataManager.mood)
            redirection()
        }
    }
    private fun redirection(){
        var intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)
    }
    private fun affichageEnregistrement(mood:String){
        Toast.makeText(this, "Votre mood est "+DataManager.mood, Toast.LENGTH_SHORT).show()
    }
    private fun enregistrerMood(mood: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("mood", mood)
            Log.d("Suivi","mood enregistré")
            apply()
        }
    }

    private fun loadSharedPrefs(){
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        DataManager.mood = sharedPref.getString("mood", null)
    }


    }



}