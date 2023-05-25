package com.example.practica2fct_alejandrosantos.data.network

import com.example.practica2fct_alejandrosantos.data.model.NumFactura
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("facturas")
    suspend fun getFacturas() : Response<NumFactura>

}