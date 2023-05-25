package com.example.practica2fct_alejandrosantos.di

import co.infinum.retromock.Retromock
import com.example.practica2fct_alejandrosantos.data.network.ApiService
import com.example.practica2fct_alejandrosantos.data.network.domain.RetromockService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Retrofit
    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid.mocklab.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun providesFacturasApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    //Retromock
    @Singleton
    @Provides
    fun provideRetromock(): Retromock {
        return Retromock.Builder()
            .retrofit(provideRetrofit())
            .build()
    }

    @Singleton
    @Provides
    fun providesFacturasRetromock(retromock: Retromock): RetromockService {
        return retromock.create(RetromockService::class.java)
    }
}