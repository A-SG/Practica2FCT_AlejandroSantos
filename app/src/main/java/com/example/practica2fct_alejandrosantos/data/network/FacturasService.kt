package com.example.practica2fct_alejandrosantos.data.network

import android.util.Log
import com.example.practica2fct_alejandrosantos.data.model.NumFactura
import com.example.practica2fct_alejandrosantos.data.network.domain.RetromockService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacturasService @Inject constructor(private val api : ApiService, private val mock : RetromockService){
    companion object{
        var variable = 0
    }
    suspend fun getFacturas() : NumFactura {
        return withContext(Dispatchers.IO){
            val response = try {
                if (api.getFacturas().isSuccessful && variable == 0){
                    api.getFacturas()
                } else {
                    mock.getFactura()
                }
            } catch (e : Exception){
                mock.getFactura()
            }

            response.body()!!
        }
    }
}