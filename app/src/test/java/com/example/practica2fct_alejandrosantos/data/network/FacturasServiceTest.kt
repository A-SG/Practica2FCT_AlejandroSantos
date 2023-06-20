package com.example.practica2fct_alejandrosantos.data.network

import com.example.practica2fct_alejandrosantos.data.network.domain.GetFacturasUseCase
import com.example.practica2fct_alejandrosantos.data.network.domain.RetromockService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FacturasServiceTest{

    @RelaxedMockK
    private lateinit var api : ApiService

    @RelaxedMockK
    private lateinit var mock : RetromockService

    lateinit var facturasService: FacturasService

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        facturasService = FacturasService(api, mock)
    }

    /*@Test
    fun `when the api doesnt return anything the get values from database`() = runBlocking {

        //Given
        coEvery { repository.getFacturasApi() } returns emptyList()

        //When
        getFacturasUseCase()

        //Then
        coVerify(exactly = 1) { repository.getFacturasDatabase() }
    }*/
}