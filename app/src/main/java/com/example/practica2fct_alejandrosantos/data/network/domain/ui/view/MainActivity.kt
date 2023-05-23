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
    private var pulsaciones = 0
    private lateinit var listadoFiltraFactura: String
    private val gson = Gson()
    @Inject
    lateinit var facturaRepository: FacturaRepository
    @Inject
    lateinit var repository: FacturaRepository
    @Inject
    lateinit var getFacturasUseCase: GetFacturasUseCase


    private  var valorFechaInicio : String = "dia/mes/año"
    private  var valorFechaFin : String = "dia/mes/año"
    private  var valorSpinner : Float = 0F
    private  var estadoCheckBoxPagadas : Boolean = false
    private  var estadoCheckBoxAnuladas : Boolean = false
    private  var estadoCheckBoxCuotaFija : Boolean = false
    private  var estadoCheckBoxPlandePago : Boolean = false
    private  var estadoCheckBoxPendientes : Boolean = false


    // Obtenemos los resoltados de la SecondActivity
    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val jsonFiltroFacturasModel = activityResult.data?.getStringExtra("ListaFiltrada")
            valorFechaInicio = activityResult.data?.getStringExtra("fechaInicio" ).toString()
            valorFechaFin = activityResult.data?.getStringExtra("fechaFin").toString()
            valorSpinner = activityResult.data?.getFloatExtra("valorSlider", 0F)!!
            estadoCheckBoxPagadas = activityResult.data?.getBooleanExtra("checkboxPagadas",false) == true
            estadoCheckBoxAnuladas = activityResult.data?.getBooleanExtra("checkboxAnuladas",false) == true
            estadoCheckBoxCuotaFija = activityResult.data?.getBooleanExtra("checkboxCuotaFija",false) == true
            estadoCheckBoxPlandePago = activityResult.data?.getBooleanExtra("checkboxPlanPago",false) == true
            estadoCheckBoxPendientes = activityResult.data?.getBooleanExtra("checkboxPendientes",false) == true


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
                        intent.putExtra("fechaInicio", valorFechaInicio)
                        intent.putExtra("fechaFin", valorFechaFin)
                        intent.putExtra("valorSlider", valorSpinner)
                        Log.d("Valorslider", valorSpinner.toString())
                        intent.putExtra("checkboxPagadas", estadoCheckBoxPagadas)
                        intent.putExtra("checkboxAnuladas", estadoCheckBoxAnuladas)
                        intent.putExtra("checkboxCuotaFija",  estadoCheckBoxCuotaFija)
                        intent.putExtra("checkboxPlanPago", estadoCheckBoxPlandePago)
                        intent.putExtra("checkboxPendientes", estadoCheckBoxPendientes)
                        responseLauncher.launch(intent)
                    }
                }
            }
        }

        //Acción que activa el Retromock al pulsar 5 veces el boton
        binding.activityMainToolbarBtnActivarRetromock.setOnClickListener() {
            pulsaciones++
            if (pulsaciones == 5) {
                facturasViewModel.onCreate()
                pulsaciones = 0
            }
        }

        //Obsevador del ViewModel
        facturasViewModel.facturas.observe(this) {
            adapter.facturas = facturasViewModel.facturas.value!!
            adapter.notifyDataSetChanged()
        }
    }
}





