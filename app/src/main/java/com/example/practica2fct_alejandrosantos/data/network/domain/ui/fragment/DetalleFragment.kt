package com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practica2fct_alejandrosantos.databinding.FragmentDetalleBinding

class DetalleFragment : Fragment() {
    private lateinit var  binding: FragmentDetalleBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetalleBinding.inflate(layoutInflater)
        return binding.root

    }
}