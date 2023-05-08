package com.example.practica2fct_alejandrosantos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practica2fct_alejandrosantos.databinding.FragmentInstalacionFrangmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnergiaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnergiaFragment : Fragment() {
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