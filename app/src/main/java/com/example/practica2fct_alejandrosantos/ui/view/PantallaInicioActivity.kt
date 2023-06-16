package com.example.practica2fct_alejandrosantos.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.data.network.FacturasService
import com.example.practica2fct_alejandrosantos.databinding.ActivityPantallaInicioBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PantallaInicioActivity : AppCompatActivity() {

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
            val intent = Intent(this, PantallaPrincipalSmartSolarActivity::class.java)
            startActivity(intent)
        }
        //Remote Config
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener(){ task->
            if (task.isSuccessful){
                val modoOscuro = Firebase.remoteConfig.getBoolean("modo_Oscuro")
                val practica1 = Firebase.remoteConfig.getBoolean("mostrar_opcion1menu")
                val practica2 = Firebase.remoteConfig.getBoolean("mostrar_opcion2menu")



                if (practica1){
                    binding.pantallaInicioCardViewPractica1.visibility = View.VISIBLE
                }
                else{
                    binding.pantallaInicioCardViewPractica1.visibility = View.GONE
                }
                if (practica2){
                    binding.pantallaInicioCardviewPractica2.visibility = View.VISIBLE
                }
                else{
                    binding.pantallaInicioCardviewPractica2.visibility = View.GONE
                }

                if (modoOscuro){
                    binding.pantallaInicio.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                    binding.pantallaInicioTvTitulo.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.pantallaInicioCardViewPractica1.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                    binding.pantallaInicioCardviewPractica2.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                    binding.pantallaInicioCardviewPractica1TvTitulo.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.pantallaInicioCardviewPractica2TvTitulo.setTextColor(ContextCompat.getColor(this, R.color.white))
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