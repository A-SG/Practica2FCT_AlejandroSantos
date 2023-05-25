package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ActivityContraseniaOlvidadaBinding
import com.example.practica2fct_alejandrosantos.databinding.ActivityLoginBinding
import com.example.practica2fct_alejandrosantos.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth

class ContraseniaOlvidada : AppCompatActivity() {
    private lateinit var binding: ActivityContraseniaOlvidadaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrasenia_olvidada)

        binding = ActivityContraseniaOlvidadaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contrasenaOlvidadaBtnEnviar.setOnClickListener {
            if(binding.contrasenaOlvidadaEdEmail.text.toString().isNotEmpty()){
                binding.contrasenaOlvidadaBtnEnviar.setOnClickListener {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(binding.contrasenaOlvidadaEdEmail.text.toString()).addOnFailureListener {
                        Toast.makeText(
                            this,
                            "El usuario no existe, revise el Email Introducido",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}