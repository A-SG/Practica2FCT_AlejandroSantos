package com.example.practica2fct_alejandrosantos.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid.mocklab.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}