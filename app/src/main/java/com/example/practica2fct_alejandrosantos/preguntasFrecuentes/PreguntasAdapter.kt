package com.example.practica2fct_alejandrosantos.preguntasFrecuentes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practica2fct_alejandrosantos.R


class PreguntasAdapter(val listaPreguntas: List<Preguntas>) : RecyclerView.Adapter<PreguntasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PreguntasViewHolder(layoutInflater.inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int =listaPreguntas.size


    override fun onBindViewHolder(holder: PreguntasViewHolder, position: Int) {
        val item = listaPreguntas[position]
        holder.render(item)
    }
}