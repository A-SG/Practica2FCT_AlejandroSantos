package com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practica2fct_alejandrosantos.databinding.FragmentInstalacionFrangmentBinding

class InstalacionFrangment : Fragment() {

    private lateinit var  binding: FragmentInstalacionFrangmentBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInstalacionFrangmentBinding.inflate(layoutInflater)
        return binding.root

    }
}