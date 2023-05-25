package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginBtnEntrar.setOnClickListener(){
            if(binding.loginEtUsuario.text?.isNotEmpty() ?:   binding.loginEtContrasena.text?.isNotEmpty() == true){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.loginEtUsuario.text.toString(),
                    binding.loginEtContrasena.text.toString()).addOnSuccessListener {
                    val intent = Intent(this, PantallaInicio::class.java)
                    startActivity(intent)
                }.addOnFailureListener {  Toast.makeText(
                    this,
                    getString(R.string.login_toast_noPosibleRegistro),
                    Toast.LENGTH_LONG
                ).show() }
            }else{
                Toast.makeText(this, getString(R.string.login_toast_camposUsuarioContraseñaVacios), Toast.LENGTH_LONG
                ).show()
            }

        }

        binding.loginTvDatosOlvidados.setOnClickListener {
            val intent = Intent(this, ContraseniaOlvidada::class.java)
            startActivity(intent)
        }

        binding.loginBtnRegistrarse.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
    }
}