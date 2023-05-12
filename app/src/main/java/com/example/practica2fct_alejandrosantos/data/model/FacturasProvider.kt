package com.example.practica2fct_alejandrosantos.data.model

import javax.inject.Inject

class FacturasProvider @Inject constructor(){
    var facturas:List<FacturaModel> = emptyList()
}