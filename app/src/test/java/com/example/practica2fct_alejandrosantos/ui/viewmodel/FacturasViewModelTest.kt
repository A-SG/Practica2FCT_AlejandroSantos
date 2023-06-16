package com.example.practica2fct_alejandrosantos.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.practica2fct_alejandrosantos.data.network.domain.GetFacturasUseCase
import com.example.practica2fct_alejandrosantos.data.network.domain.model.Factura
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi

class FacturasViewModelTest{

    @RelaxedMockK
    private lateinit var getFacturasUseCase: GetFacturasUseCase

    private lateinit var facturasViewModel: FacturasViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        facturasViewModel = FacturasViewModel(getFacturasUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created at the first time, get all facturas`() =
        runTest {

            //Given
            val listaFacturas = listOf(
                Factura("Pagada", 25.14, "05/02/2019"),
                Factura("Pendiente de pago", 40.20, "09/06/2022")
            )
            coEvery { getFacturasUseCase() } returns listaFacturas

            //When
            facturasViewModel.onCreate()

            //Then
            facturasViewModel.facturas.value == listaFacturas
        }

}