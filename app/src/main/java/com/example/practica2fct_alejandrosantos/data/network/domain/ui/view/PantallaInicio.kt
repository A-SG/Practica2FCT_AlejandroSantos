package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.practica2fct_alejandrosantos.data.FacturaRepository
import com.example.practica2fct_alejandrosantos.data.adapter.FacturasAdapter
import com.example.practica2fct_alejandrosantos.databinding.ActivityMainBinding
import com.example.practica2fct_alejandrosantos.data.network.domain.GetFacturasUseCase
import com.example.practicaprueba.data.network.domain.model.Factura
import com.example.practica2fct_alejandrosantos.data.network.domain.ui.viewmodel.FacturasViewModel
import com.example.practica2fct_alejandrosantos.databinding.ActivityPantallaInicioBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PantallaInicio : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPantallaInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.practica1.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.practica2.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}