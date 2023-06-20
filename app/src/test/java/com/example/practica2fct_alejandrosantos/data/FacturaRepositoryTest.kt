package com.example.practica2fct_alejandrosantos.data

import com.example.practica2fct_alejandrosantos.data.database.dao.FacturaDao
import com.example.practica2fct_alejandrosantos.data.model.FacturaModel
import com.example.practica2fct_alejandrosantos.data.network.FacturasService
import com.example.practica2fct_alejandrosantos.data.network.domain.GetFacturasUseCase
import com.example.practica2fct_alejandrosantos.data.network.domain.model.Factura
import com.example.practica2fct_alejandrosantos.ui.viewmodel.FacturasViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FacturaRepositoryTest{

    @RelaxedMockK
    private lateinit var facturasDao: FacturaDao
    @RelaxedMockK
    private lateinit var apiService: FacturasService

    private lateinit var facturaRepository: FacturaRepository


    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        facturaRepository= FacturaRepository(facturasDao,apiService)
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {

        //val lista =  listOf( FacturaModel(1,"Pagada", 25.14, "05/02/2019"), FacturaModel(2,"Pendiente de pago", 40.20, "09/06/2022"))
        //Given
        coEvery { apiService.getFacturas().facturas } returns emptyList()

        //When
       val respose = facturaRepository.getFacturasApi()

        //Then
        coVerify(exactly = 1) { facturaRepository.getFacturasApi() }
        assert(respose.isEmpty())
    }


}