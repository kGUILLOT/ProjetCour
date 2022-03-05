package com.example.projetcour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        loadSharedPrefs()
        var condition =findViewById<CheckBox>(R.id.checkBox)
        if (DataManager.connexion==0){
            var activity=findViewById<Button>(R.id.button)
            activity.setOnClickListener {
                if (findViewById<CheckBox>(R.id.checkBox).isChecked) {
                    var intent = Intent(this, AgendaActivity::class.java)

                    enregistrerConnexion()
                    enregistrerImage()

                    if (DataManager.avatar==0) {
                        findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.man)
                    }
                    if (DataManager.avatar==1) {
                        findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.man2)
                    }
                    if (DataManager.avatar==2) {
                        findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.woman)
                    }
                    startActivity(intent)
                    Log.d("Suivi","mon img est "+DataManager.avatar)
                } else {
                    Toast.makeText(this, "Veuillez accepter les conditions", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }else{
            var activity=findViewById<Button>(R.id.button)
            activity.setOnClickListener{

            }
            findViewById<TextView>(R.id.texteCoach).setText("Te voila de retour")
            condition.isVisible = false
            condition.isChecked = true
            Log.d("Suivi","ma co est "+DataManager.connexion)

        }


    }
    private fun enregistrerImage(){
        var image= Random.nextInt(0,3)
        if (image==0) {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putInt("avatar", image)
                apply()
            }
        }
        if (image==1) {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putInt("avatar", image)
                apply()
            }
        }
        if (image==2) {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putInt("avatar", image)
                apply()
            }
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