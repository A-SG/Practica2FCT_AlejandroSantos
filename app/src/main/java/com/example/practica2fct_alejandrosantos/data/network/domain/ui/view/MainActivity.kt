package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.data.FacturaRepository
import com.example.practica2fct_alejandrosantos.data.adapter.FacturasAdapter
import com.example.practica2fct_alejandrosantos.data.network.domain.GetFacturasUseCase
import com.example.practica2fct_alejandrosantos.data.network.domain.ui.viewmodel.FacturasViewModel
import com.example.practica2fct_alejandrosantos.databinding.ActivityMainBinding
import com.example.practica2fct_alejandrosantos.data.network.domain.model.Factura
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //Variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FacturasAdapter
    private val facturasViewModel: FacturasViewModel by viewModels()
    private lateinit var listadoFiltraFactura: String
    private val gson = Gson()
    @Inject
    lateinit var facturaRepository: FacturaRepository
    @Inject
    lateinit var repository: FacturaRepository
    @Inject
    lateinit var getFacturasUseCase: GetFacturasUseCase



    // Obtenemos los resoltados de la SecondActivity
    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val jsonFiltroFacturasModel = activityResult.data?.getStringExtra("ListaFiltrada")

        if (activityResult.resultCode == RESULT_OK) {
                adapter.facturas = gson.fromJson(
                    jsonFiltroFacturasModel,
                    object : TypeToken<List<Factura?>?>() {}.type
                )
                facturasViewModel.facturas.value = adapter.facturas
                adapter.notifyDataSetChanged()

            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FacturasAdapter(emptyList())
        binding.activityMainRvFacturas.adapter = adapter
        facturasViewModel.onCreate()


        //Botón para pasar a la SecondActivity ( Actividad de fitros)
        binding.actvityMainToolbarIbtnFiltros.setOnClickListener() {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val result = getFacturasUseCase()
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)

                    if (result.isNotEmpty()) {
                        listadoFiltraFactura = gson.toJson(result)
                        intent.putExtra("listaFacturasSinFiltrar", listadoFiltraFactura)
                        responseLauncher.launch(intent)
                    }
                }
            }
        }
        //Obsevador del ViewModel
        facturasViewModel.facturas.observe(this) {
            adapter.facturas = facturasViewModel.facturas.value!!
            adapter.notifyDataSetChanged()
        }
    }
}





