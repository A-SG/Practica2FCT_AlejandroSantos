package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.centroAyudaActivity
import com.example.practica2fct_alejandrosantos.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



      val configSettings : FirebaseRemoteConfigSettings = remoteConfigSettings {
          minimumFetchIntervalInSeconds = 60
      }
        val firebaseConfig:FirebaseRemoteConfig = Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.setDefaultsAsync(mapOf("mostra_Lista" to false))

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener(){task->
            if (task.isSuccessful){
                val modoOscuro = Firebase.remoteConfig.getBoolean("modo_Oscuro")

                if (modoOscuro){
                    binding.activityLogin.setBackgroundColor(resources.getColor(R.color.black))
                    binding.loginEtUsuario.setBackgroundColor(resources.getColor(R.color.white))
                    binding.loginEtContrasena.setBackgroundColor(resources.getColor(R.color.white))
                    binding.loginTvDatosOlvidados.setTextColor(resources.getColor(R.color.white))
                    binding.loginCbRecordarContrasenia.setTextColor(resources.getColor(R.color.white))
                    binding.textView.setTextColor(resources.getColor(R.color.white))
                    binding.ayudaTV.setTextColor(resources.getColor(R.color.white))
                    binding.divider.setBackgroundColor(resources.getColor(R.color.white))
                    binding.divider2.setBackgroundColor(resources.getColor(R.color.white))
                }
            }
        }




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
        binding.ayudaTV.setOnClickListener(){

            val intent = Intent(this, centroAyudaActivity::class.java)
            startActivity(intent)
        }
    }
}