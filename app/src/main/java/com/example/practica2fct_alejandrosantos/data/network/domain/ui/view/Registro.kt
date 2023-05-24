package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2fct_alejandrosantos.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

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
                    val intent = Intent(this, PantallaInicio::class.java)
                    startActivity(intent)
                }  }



            } catch (e: FirebaseAuthWeakPasswordException) {
                Toast.makeText(
                    this,
                    "La contraseña debe de tener 6 caracteres como mínimo",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(this, "El correo no es válido", Toast.LENGTH_SHORT).show()
            } catch (e: FirebaseAuthUserCollisionException) {
                Toast.makeText(this, "El usuario introducido ya existe", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}