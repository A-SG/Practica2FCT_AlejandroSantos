package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import com.example.practica2fct_alejandrosantos.data.network.domain.ui.fragment.DatePickerFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.data.FacturaRepository
import com.example.practica2fct_alejandrosantos.databinding.ActivitySecondBinding
import com.example.practicaprueba.data.network.domain.model.Factura
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil


@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding


    @Inject
    lateinit var facturaRepository: FacturaRepository
    private lateinit var facturas: List<Factura>
    private lateinit var jsonFiltroFacturasModel: String
    private var json: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locale = Locale("es")
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        config.setLocale(locale)
        baseContext.createConfigurationContext(config)


        binding.activitySecondToolbarImgBtnSalir.setOnClickListener() {
            finish()
        }

        binding.activitySecondCardviewFiltroFechaBtnFechaini.setOnClickListener() {
            mostrarDatepicker()
        }

        binding.activitySecondCardviewFiltroFechaBtnFechaFin.setOnClickListener() {
            mostrarDatepickerFin()
        }

        //Acción del botón de "Borrar filtros"
        binding.activitySecondBtnBorrarFiltro.setOnClickListener() {
            binding.activitySecondCardviewFiltroFechaBtnFechaini.setText(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio)
            binding.activitySecondCardviewFiltroFechaBtnFechaFin.setText(R.string.activitySecond_cardviewFiltroFecha_textBtnFechaFin)
            binding.activitySecondCardviewFiltroImporteSlImporte.value = 0F
            binding.activitySecondCardviewFiltroEstadoCbpagadas.isChecked = false
            binding.activitySecondCardviewFiltroEstadoCbanuladas.isChecked = false
            binding.activitySecondCardviewFiltroEstadoCbanuladas.isChecked = false
            binding.activitySecondCardviewFiltroEstadoCbpendientesPago.isChecked = false
            binding.activitySecondCardviewFiltroEstadoCbplanDePago.isChecked = false
        }

        binding.activitySecondCardviewFiltroImporteSlImporte.addOnChangeListener { slider, value, fromUser ->
            binding.variacionImporte.text =getString(R.string.itemFacturas_simboloMoneda, ceil(value).toInt().toString())
        }


        //Botón de aplicar filtros a la correspondintes facturas
        binding.activitySecondBtnAplicarFiltro.setOnClickListener() {
            val resultIntent = Intent()
            val listaFacturas: String
            val json = Gson()
            listaFacturas = json.toJson(getParametrosEntradaActividad())
            Log.d("ListaFiltradaParaIntent", listaFacturas)
            resultIntent.putExtra("ListaFiltrada", listaFacturas)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        jsonFiltroFacturasModel = intent.getStringExtra("listaFacturasSinFiltrar").toString()
        facturas = json.fromJson(jsonFiltroFacturasModel, object : TypeToken<List<Factura?>?>() {}.type)
        val ordenPorImporte = facturas.sortedByDescending { facturas: Factura -> facturas.importeOrdenacion }
        binding.tvImporteMaximo.text = getString(R.string.itemFacturas_simboloMoneda,ordenPorImporte.first().importeOrdenacion.toInt() + 1)
        binding.activitySecondCardviewFiltroImporteSlImporte.valueTo = ceil(ordenPorImporte.first().importeOrdenacion).toFloat()
        binding.activitySecondCardviewFiltroImporteSlImporte.value = 0.0.toFloat()
        binding.activitySecondCardviewFiltroImporteTvImporteMinimo.text = getString(R.string.itemFacturas_simboloMoneda, getString( R.string.activitySecond_cardviewFiltroImporte_tvImporteMinimo))

        Log.d("listaparaspinner", facturas.toString())
    }

    private fun mostrarDatepicker() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.activitySecondCardviewFiltroFechaBtnFechaini.text = "$day/$month/$year"
    }

    fun onDateSelectedEnd(day: Int, month: Int, year: Int) {
        binding.activitySecondCardviewFiltroFechaBtnFechaFin.text = "$day/$month/$year"
    }

    private fun mostrarDatepickerFin() {
        val datePicker =
            DatePickerFragment { day, month, year -> onDateSelectedEnd(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    //Función de filtrado de facturas
    private fun getParametrosEntradaActividad(): List<Factura> {
        //Variables
        jsonFiltroFacturasModel = intent.getStringExtra("listaFacturasSinFiltrar").toString()
        var listaFiltrada = emptyList<Factura>()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val firstDate: Date
        val secondDate: Date

        //Obtención de listado de facturas procedentes del main activity
        if (jsonFiltroFacturasModel != null && jsonFiltroFacturasModel.isNotEmpty()) {
            facturas = json.fromJson(
                jsonFiltroFacturasModel,
                object : TypeToken<List<Factura?>?>() {}.type
            )
            var pagadas = emptyList<Factura>()
            var anuladas = emptyList<Factura>()
            var cuotaFija = emptyList<Factura>()
            var planPago = emptyList<Factura>()
            var pendientesPago = emptyList<Factura>()

            //Filtrado `poer el estado de la factura
            if (binding.activitySecondCardviewFiltroEstadoCbpagadas.isChecked) {
                pagadas = facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbpagadas)}
            }
            if (binding.activitySecondCardviewFiltroEstadoCbanuladas.isChecked) {
                anuladas = facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbanuladas)}
            }

            if (binding.activitySecondCardviewFiltroEstadoCbcuotafija.isChecked) {
                cuotaFija = facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbcuotafija)}
            }

            if (binding.activitySecondCardviewFiltroEstadoCbplanDePago.isChecked) {
                planPago = facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbplanDePago)}
            }

            if (binding.activitySecondCardviewFiltroEstadoCbpendientesPago.isChecked) {
                pendientesPago = facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbpendientesPago) }
            }

            var listaPorEstado = pagadas + anuladas + planPago + cuotaFija + pendientesPago

            if (listaPorEstado.isEmpty()) {
                listaFiltrada = facturas
            } else {
                listaFiltrada = listaPorEstado
            }

            //Filtrado de facturas por fecha de inicio y fecha de fin
            if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() != getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() != getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio)  ){
                firstDate = formatoFecha.parse(binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString())
                secondDate = formatoFecha.parse(binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString())

                listaFiltrada = listaFiltrada.filter { factura: Factura -> formatoFecha.parse(factura.fecha) >= secondDate && formatoFecha.parse(factura.fecha) <= firstDate}
            }
            else  if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() == getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() != getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) ){
                firstDate = formatoFecha.parse(binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString())
                listaFiltrada = listaFiltrada.filter { factura: Factura -> formatoFecha.parse(factura.fecha) <= firstDate}

            }else  if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() != getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() == getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio)  ){

                secondDate = formatoFecha.parse(binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString())
                listaFiltrada = listaFiltrada.filter { factura: Factura -> formatoFecha.parse(factura.fecha) >= secondDate }
            }
            else if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() == getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() == getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) )

            //Filtrado de factura por su importe
            if (listaFiltrada.isEmpty()) {
                listaFiltrada = facturas.filter { factura: Factura -> factura.importeOrdenacion <= binding.activitySecondCardviewFiltroImporteSlImporte.value.toDouble() }
            } else {
                if (binding.activitySecondCardviewFiltroImporteSlImporte.value != 0.0.toFloat()) {
                    listaFiltrada = listaFiltrada.filter { factura: Factura -> factura.importeOrdenacion <= binding.activitySecondCardviewFiltroImporteSlImporte.value.toDouble() }
                }
            }
        }

        //Si no se realiza ningunfiltro sobre la lista, devolverá la misma lista cargada al principio
        if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text == getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio)  && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text == getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio)  && binding.activitySecondCardviewFiltroImporteSlImporte.value == 0.0.toFloat() &&
            !binding.activitySecondCardviewFiltroEstadoCbpagadas.isChecked && !binding.activitySecondCardviewFiltroEstadoCbanuladas.isChecked && !binding.activitySecondCardviewFiltroEstadoCbcuotafija.isChecked && !binding.activitySecondCardviewFiltroEstadoCbplanDePago.isChecked && !binding.activitySecondCardviewFiltroEstadoCbpendientesPago.isChecked
        ) {
            listaFiltrada = facturas
        }
        return listaFiltrada
    }
}