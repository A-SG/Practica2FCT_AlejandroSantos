package com.example.practica2fct_alejandrosantos.data.network.domain

import com.example.practica2fct_alejandrosantos.data.FacturaRepository
import com.example.practica2fct_alejandrosantos.data.model.toDatabase
import com.example.practica2fct_alejandrosantos.data.network.domain.model.Factura
import javax.inject.Inject

class GetFacturasUseCase @Inject constructor(private val repository : FacturaRepository){

    suspend operator fun invoke(): List<Factura> {
        val facturas = repository.getFacturasApi()

        return if (facturas.isNotEmpty()){
            repository.clearFacturas()
            repository.insertFacturas(facturas.map { it.toDatabase() })
            facturas
        }else{
            repository.getFacturasDatabase()
        }
    }
}