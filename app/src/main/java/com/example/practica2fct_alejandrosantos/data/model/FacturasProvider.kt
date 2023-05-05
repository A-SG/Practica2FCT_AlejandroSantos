package com.example.practica2fct_alejandrosantos.data.model

import com.example.practica2fct_alejandrosantos.data.model.FacturaModel
import javax.inject.Inject

class FacturasProvider @Inject constructor(){
    var facturas:List<FacturaModel> = emptyList()
}