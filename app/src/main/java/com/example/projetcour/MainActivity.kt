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
        initDatabase()

    }

    override fun onStart() {
        super.onStart()
        loadSharedPrefs()

        var condition =findViewById<CheckBox>(R.id.checkBox)
        if (DataManager.connexion==0){//première connexion
            var activity=findViewById<Button>(R.id.button)
            activity.setOnClickListener {
                if (findViewById<CheckBox>(R.id.checkBox).isChecked) {//si les conditions sont cochés
                    var intent = Intent(this, AgendaActivity::class.java)
                    enregistrerConnexion()
                    enregistrerImage()//stockage d'une valeur aléatoire pour l'image
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Veuillez accepter les conditions", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }else{// seconde ou plus connexion
            //afichage de l'image a l'utilisateur
            if (DataManager.avatar==0) {
                findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.man)
            }
            if (DataManager.avatar==1) {
                findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.man2)
            }
            if (DataManager.avatar==2) {
                findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.woman)
            }


            var activity=findViewById<Button>(R.id.button)
            activity.setOnClickListener{
                var intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
            }
            findViewById<TextView>(R.id.texteCoach).setText("Te voila de retour")
            condition.isVisible = false
            condition.isChecked = true
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

    private fun enregistrerImage(){
         val number= listOf(0,1,2)
        val image=number.shuffled().last()
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


}