package com.example.practica2fct_alejandrosantos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practica2fct_alejandrosantos.databinding.FragmentInstalacionFrangmentBinding


class DetalleFragment : Fragment() {
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