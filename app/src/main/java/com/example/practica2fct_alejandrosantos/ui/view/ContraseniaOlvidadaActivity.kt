package com.example.practica2fct_alejandrosantos.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ActivityContraseniaOlvidadaBinding
import com.google.firebase.auth.FirebaseAuth

class ContraseniaOlvidadaActivity : AppCompatActivity() {
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
                            getString(R.string.contraseñaolvidada_toast_usuarioExistente),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}