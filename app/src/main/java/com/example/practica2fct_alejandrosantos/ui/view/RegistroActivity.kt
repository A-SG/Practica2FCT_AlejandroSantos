package com.example.practica2fct_alejandrosantos.ui.view


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private  var excecepcion : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrateBtnregistrarse.setOnClickListener {


            try {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.registroEdUsuario.text.toString(),
                    binding.registroEdContrasena.text.toString()).addOnSuccessListener {    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.registroEdUsuario.text.toString(),
                    binding.registroEdContrasena.text.toString()).addOnSuccessListener {
                    val intent = Intent(this, PantallaInicioActivity::class.java)
                    startActivity(intent)
                }  }



            } catch (e: FirebaseAuthWeakPasswordException) {
                Toast.makeText(
                    this,
                    getString(R.string.registro_toast_formatoContraseña),
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: FirebaseAuthInvalidCredentialsException) {

            } catch (e: FirebaseAuthUserCollisionException) {

            } catch (e: Exception) {
                excecepcion = 3
            }

            when (excecepcion) {
                3 -> Toast.makeText(this, getString(R.string.registro_toast_errorRegistro), Toast.LENGTH_LONG).show()
                else -> getString(R.string.registro_toast_errorRegistroDefecto)
            }
        }
    }
}