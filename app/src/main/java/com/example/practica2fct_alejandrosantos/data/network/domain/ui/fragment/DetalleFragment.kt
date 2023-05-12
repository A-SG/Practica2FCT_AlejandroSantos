package com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.FragmentDetalleBinding


class DetalleFragment : Fragment() {
    private lateinit var  binding: FragmentDetalleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentDetalleImageButtonInformacion.setOnClickListener() {
            binding.fragmentDetalleImageButtonInformacion.setBackgroundResource(R.drawable.infoicon_azul_pressed)
            mostrarPopup()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentDetalleBinding.inflate(layoutInflater)
        return binding.root
    }



    //Función para mostrar popup
    @SuppressLint("MissingInflatedId")
    private fun mostrarPopup(){
        val builder = AlertDialog.Builder(context)
        val customView = LayoutInflater.from(context).inflate(R.layout.popup_fragmentdetalle, null)
        builder.setView(customView)

        val dialog = builder.create()
        val btnAceptar = customView.findViewById<Button>(R.id.popupFragmentDetalle_btn)

        btnAceptar.setOnClickListener(){
            dialog.dismiss()
        }
        dialog.show()

    }
}