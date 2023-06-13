package com.example.practica2fct_alejandrosantos.preguntasFrecuentes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica2fct_alejandrosantos.CentroAyudaActivity
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ActivityPreguntasFrecuentesBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig


class PreguntasFrecuentesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreguntasFrecuentesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntasFrecuentesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.preguntasFrecuentesToolbarImgBtnSalir.setOnClickListener(){
            val intent = Intent(this, CentroAyudaActivity::class.java)
            startActivity(intent)
        }
        initReciclerView()
    }

    fun initReciclerView(){
        binding.preguntasFrecuentesRecyclerViewPreguntas.layoutManager = LinearLayoutManager(this)
        binding.preguntasFrecuentesRecyclerViewPreguntas.adapter = PreguntasAdapter(
            PreguntasProvider.listaPreguntas
        )
    }
}