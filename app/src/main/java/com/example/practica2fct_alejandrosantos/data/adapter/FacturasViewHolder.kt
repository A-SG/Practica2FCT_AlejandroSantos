package com.example.practica2fct_alejandrosantos.data.adapter

import android.annotation.SuppressLint
import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.databinding.ItemFacturaBinding
import com.example.practicaprueba.data.network.domain.model.Factura
import java.text.SimpleDateFormat
import java.util.*


class FacturasViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var popupactivo: Boolean = false

    //Uso de binding para acceder directamente a la vista
    val binding = ItemFacturaBinding.bind(view)


    //Función que se llama por cada uno de los items del recyclerView

    @SuppressLint("MissingInflatedId")
    fun bind(factura: Factura) {
        val formatoFechaEntrada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatoFechaSalida = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        var cambioFecha = factura.fecha
        val fechaFactura: Date = formatoFechaEntrada.parse(cambioFecha)
        val fechaFormateada: String = formatoFechaSalida.format(fechaFactura)

        if (factura.descEstado == itemView.context.getString(R.string.activitySecond_cardviewFiltroEstado_cbpagadas)) {
            binding.itemFacturaTvEstadoFactura.text = ""
        }
        else{
            binding.itemFacturaTvEstadoFactura.text = factura.descEstado
        }
        binding.itemFacturaTvImporteFactura.text = itemView.context.getString(R.string.itemFacturas_simboloMoneda, factura.importeOrdenacion.toString())
        binding.itemFacturaTvFechaFactura.text = fechaFormateada

        //Hacer click en cada una de las celdas
        itemView.setOnClickListener(View.OnClickListener {
            if (!popupactivo) {
                popupactivo = true
                val popup = PopupWindow(itemView.context)
                val popupView =
                    LayoutInflater.from(itemView.context).inflate(R.layout.popup_factura, null)
                popup.contentView = popupView
                popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
                popup.height = ViewGroup.LayoutParams.WRAP_CONTENT
                popup.showAtLocation(popupView, 1, 0, 0)

                val closeButton = popupView.findViewById<Button>(R.id.popUpFactura_btnAceptar)
                closeButton.setOnClickListener {
                    popup.dismiss()
                    popupactivo = false
                }
                popup.showAsDropDown(itemView)
            }
        })
    }
}