package com.example.practica2fct_alejandrosantos.averias

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ActivityNotificacionAveriasBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig


class NotificacionAveriasActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityNotificacionAveriasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificacionAveriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener(){ task->
            if (task.isSuccessful){
                val modoOscuro = Firebase.remoteConfig.getBoolean("modo_Oscuro")

                if (modoOscuro){
                    binding.averias.setBackgroundColor(resources.getColor(R.color.black))
                    binding.tvTitleAveria.setTextColor(resources.getColor(R.color.white))
                    binding.spTipoAveria.setBackgroundColor(resources.getColor(R.color.gris))
                    binding.tietMotivoAveria.setBackgroundColor(resources.getColor(R.color.gris))
                }
            }
        }

        val aaAveria = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item)

        aaAveria.addAll(arrayListOf(
            baseContext.resources.getString(R.string.activity_notificacion_averias_spinnerItem1),
            baseContext.resources.getString(R.string.activity_notificacion_averias_spinnerItem2),
            baseContext.resources.getString(R.string.activity_notificacion_averias_spinnerItem3)))

        binding.imgBtnSalir2.setOnClickListener{
            finish()
        }
        binding.spTipoAveria.adapter = aaAveria

        binding.btEnviarAveria.setOnClickListener {
            Toast.makeText(
                this,
                baseContext.resources.getString(R.string.activity_notificacion_averias_enviarAveria),
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }
}