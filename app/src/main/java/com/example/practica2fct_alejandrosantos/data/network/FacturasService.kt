package com.example.practica2fct_alejandrosantos.data.network

import android.util.Log
import com.example.practica2fct_alejandrosantos.data.model.NumFactura
import com.example.practica2fct_alejandrosantos.data.network.domain.RetromockService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FacturasService @Inject constructor(private val api : ApiService, private val mock : RetromockService){
    suspend fun getFacturas() : NumFactura {
        return withContext(Dispatchers.IO){
            val response = try {
                if (api.getFacturas().isSuccessful){
                    api.getFacturas()
                } else {
                    mock.getFactura()
                }
            } catch (e : Exception){
                mock.getFactura()
            }
            Log.d("ListaConRetromock" , response.body().toString())
            response.body()!!
        }
    }
}