package com.example.practica2fct_alejandrosantos.data.network.domain.model

import com.example.practica2fct_alejandrosantos.data.model.FacturaModel

data class Factura (var descEstado : String, var importeOrdenacion: Double, var fecha: String)

fun FacturaModel.toDomain() = Factura(descEstado, importeOrdenacion, fecha)
