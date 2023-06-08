package com.example.practica2fct_alejandrosantos.preguntasFrecuentes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica2fct_alejandrosantos.centroAyudaActivity
import com.example.practica2fct_alejandrosantos.databinding.ActivityPreguntasFrecuentesBinding


class PreguntasFrecuentesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreguntasFrecuentesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreguntasFrecuentesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.preguntasFrecuentesToolbarImgBtnSalir.setOnClickListener(){
            val intent = Intent(this, centroAyudaActivity::class.java)
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