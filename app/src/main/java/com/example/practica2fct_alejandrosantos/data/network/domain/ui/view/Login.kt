package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ActivityLoginBinding
import com.example.practica2fct_alejandrosantos.databinding.ActivityMainBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}