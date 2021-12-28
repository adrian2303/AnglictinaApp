package com.example.anglictinaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class HlavnaAktivita : AppCompatActivity() {
    private lateinit var tlacidloSlovnik: Button
    private lateinit var tlacidloUpravy: Button
    private lateinit var tlacidloInformacne: Button
    private lateinit var nazovAplikacie: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hlavna_aktivita)

        incializaciaObrazovky()
        tlacidloUpravy.setOnClickListener { zobrazenie() }
        tlacidloInformacne.setOnClickListener { informacieOAplikacii() }
    }

    private fun incializaciaObrazovky(){
        tlacidloSlovnik = findViewById(R.id.tlacidloSlovnik)
        tlacidloUpravy = findViewById(R.id.tlacidloUpravy)
        tlacidloInformacne = findViewById(R.id.tlacidloInformacne)
        nazovAplikacie = findViewById(R.id.nazovAplikacie)
    }


    private fun zobrazenie(){
        val zamer = Intent(this, MainActivity::class.java)
        zamer.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(zamer)
    }

    private fun informacieOAplikacii(){
        Toast.makeText(this,getString(R.string.toastInformacieOAplikacii), Toast.LENGTH_LONG).show()
    }
}