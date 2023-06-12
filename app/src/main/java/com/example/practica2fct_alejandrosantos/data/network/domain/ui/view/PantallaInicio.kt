package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.data.network.FacturasService
import com.example.practica2fct_alejandrosantos.databinding.ActivityPantallaInicioBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PantallaInicio : AppCompatActivity() {

    private lateinit var binding: ActivityPantallaInicioBinding
    private var pulsaciones = 0


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
        //Remote Config
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener(){ task->
            if (task.isSuccessful){
                val modoOscuro = Firebase.remoteConfig.getBoolean("modo_Oscuro")

                if (modoOscuro){
                    binding.pantallaInicio.setBackgroundColor(resources.getColor(R.color.black))
                    binding.pantallaInicioTvTitulo.setTextColor(resources.getColor(R.color.white))
                    binding.pantallaInicioCardViewPractica1.setBackgroundColor(resources.getColor(R.color.black))
                    binding.pantallaInicioCardviewPractica2.setBackgroundColor(resources.getColor(R.color.black))
                    binding.pantallaInicioCardviewPractica1TvTitulo.setTextColor(resources.getColor(R.color.white))
                    binding.pantallaInicioCardviewPractica2TvTitulo.setTextColor(resources.getColor(R.color.white))
                }
            }
        }

        binding.pantallaInicioBtnRetromock.text= getString(R.string.pantallaInicio_toast_Retrofit)
        //Acción que activa el Retromock al pulsar 5 veces el boton
        binding.pantallaInicioBtnRetromock.setOnClickListener() {
            pulsaciones++
            if (pulsaciones == 5) {

                if (FacturasService.variable == 0 ){
                    FacturasService.variable = 1
                    Toast.makeText(this, getString(R.string.pantallaInicio_toast_RetroMok), Toast.LENGTH_LONG).show()
                    binding.pantallaInicioBtnRetromock.text = getString(R.string.pantallaInicio_toast_RetroMok)
                }else
                {
                    FacturasService.variable = 0
                    Toast.makeText(this, getString(R.string.pantallaInicio_toast_Retrofit), Toast.LENGTH_LONG).show()
                    binding.pantallaInicioBtnRetromock.text = getString(R.string.pantallaInicio_toast_Retrofit)
                }
                pulsaciones = 0
            }
        }
    }
}