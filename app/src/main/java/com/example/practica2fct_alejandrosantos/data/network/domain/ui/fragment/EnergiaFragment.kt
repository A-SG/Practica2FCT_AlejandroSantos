package com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.practica2fct_alejandrosantos.databinding.FragmentEnergiaBinding

class EnergiaFragment : Fragment() {
    private lateinit var binding: FragmentEnergiaBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentEnergiaBinding.inflate(layoutInflater)
        return binding.root

    }
}