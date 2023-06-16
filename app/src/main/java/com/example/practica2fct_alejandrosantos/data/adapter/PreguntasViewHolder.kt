package com.example.practica2fct_alejandrosantos.data.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.data.model.Preguntas
import com.example.practica2fct_alejandrosantos.databinding.ItemViewBinding


class PreguntasViewHolder(view: View): RecyclerView.ViewHolder(view){

    val binding = ItemViewBinding.bind(view)

    @SuppressLint("ResourceAsColor")
    fun render(preguntas: Preguntas){



        binding.preguntasFrecuentesTvTituloPregunta.text = preguntas.pregunta
        binding.preguntasFrecuentesTvCuerpoPregunta.text = preguntas.cuerpoPregunta
        binding.preguntasFrecuentesBtnDeplegarCuerpoPregunta.setOnClickListener(){
            if(binding.preguntasFrecuentesTvCuerpoPregunta.visibility == View.GONE){
                binding.preguntasFrecuentesTvCuerpoPregunta.visibility = View.VISIBLE
                binding.preguntasFrecuentesBtnDeplegarCuerpoPregunta.setImageResource(R.drawable.flecha_arriba)
            }else{
                binding.preguntasFrecuentesTvCuerpoPregunta.visibility = View.GONE
                binding.preguntasFrecuentesBtnDeplegarCuerpoPregunta.setImageResource(R.drawable.flecha_bajo)
            }
        }
    }

}