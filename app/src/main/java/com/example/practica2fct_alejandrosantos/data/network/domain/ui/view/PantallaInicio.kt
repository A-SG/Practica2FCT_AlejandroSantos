package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica2fct_alejandrosantos.databinding.ActivityPantallaInicioBinding


class PantallaInicio : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPantallaInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Método que dirige a la práctica 1
        binding.pantallaInicioCardviewPractica1ImgBtnPractica1.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //Método que dirige a la práctica 2
        binding.pantallaInicioCardviewPractica2ImgBtnPractica2.setOnClickListener() {
            val intent = Intent(this, PantallaPrincipalSmartSolar::class.java)
            startActivity(intent)
        }
    }
}