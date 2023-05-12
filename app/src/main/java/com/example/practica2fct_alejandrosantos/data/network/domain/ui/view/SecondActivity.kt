package com.example.practica2fct_alejandrosantos.data.network.domain.ui.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.data.FacturaRepository
import com.example.practica2fct_alejandrosantos.databinding.ActivitySecondBinding
import com.example.practica2fct_alejandrosantos.data.network.domain.model.Factura
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.ceil


@AndroidEntryPoint
class SecondActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivitySecondBinding
    private val calendarfechaDesde = Calendar.getInstance()
    private val calendarfechHasta = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd MMMM yyyy")
    private lateinit var picker: DatePickerDialog
    private lateinit var pickerfin: DatePickerDialog
    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
    private var fecha: Int = 0
    private var fechainicio: Int = 1
    private var fechafin: Int = 2


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
            fecha = fechainicio
            picker = DatePickerDialog(
                this,
                this,
                calendarfechaDesde.get(Calendar.YEAR),
                calendarfechaDesde.get(Calendar.MONTH),
                calendarfechaDesde.get(Calendar.DAY_OF_MONTH)
            )
            //picker.datePicker.maxDate = calendarfechaDesde.timeInMillis
            picker.show()
        }

        binding.activitySecondCardviewFiltroFechaBtnFechaFin.setOnClickListener() {
            fecha = fechafin
            pickerfin = DatePickerDialog(
                this,
                this,
                calendarfechHasta.get(Calendar.YEAR),
                calendarfechHasta.get(Calendar.MONTH),
                calendarfechHasta.get(Calendar.DAY_OF_MONTH)
            )
            if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text != getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio)) {
                val fecha =
                    formatter.parse(binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString())
                calendarfechHasta.timeInMillis = fecha.time
                pickerfin.datePicker.minDate = calendarfechHasta.timeInMillis
                pickerfin.show()
            } else {
                pickerfin.datePicker.minDate = calendarfechHasta.timeInMillis
                pickerfin.show()
            }

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
            binding.variacionImporte.text =
                getString(R.string.itemFacturas_simboloMoneda, ceil(value).toInt().toString())
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
        val ordenPorImporte =
            facturas.sortedByDescending { facturas: Factura -> facturas.importeOrdenacion }
        binding.tvImporteMaximo.text = getString(
            R.string.itemFacturas_simboloMoneda,
            ordenPorImporte.first().importeOrdenacion.toInt() + 1
        )
        binding.activitySecondCardviewFiltroImporteSlImporte.valueTo =
            ceil(ordenPorImporte.first().importeOrdenacion).toFloat()
        binding.activitySecondCardviewFiltroImporteSlImporte.value = 0.0.toFloat()
        binding.activitySecondCardviewFiltroImporteTvImporteMinimo.text = getString(
            R.string.itemFacturas_simboloMoneda,
            getString(R.string.activitySecond_cardviewFiltroImporte_tvImporteMinimo)
        )

        Log.d("listaparaspinner", facturas.toString())
    }


    //Función de filtrado de facturas
    private fun getParametrosEntradaActividad(): List<Factura> {
        //Variables
        jsonFiltroFacturasModel = intent.getStringExtra("listaFacturasSinFiltrar").toString()
        var listaFiltrada = emptyList<Factura>()

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
                pagadas =
                    facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbpagadas) }
            }
            if (binding.activitySecondCardviewFiltroEstadoCbanuladas.isChecked) {
                anuladas =
                    facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbanuladas) }
            }

            if (binding.activitySecondCardviewFiltroEstadoCbcuotafija.isChecked) {
                cuotaFija =
                    facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbcuotafija) }
            }

            if (binding.activitySecondCardviewFiltroEstadoCbplanDePago.isChecked) {
                planPago =
                    facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbplanDePago) }
            }

            if (binding.activitySecondCardviewFiltroEstadoCbpendientesPago.isChecked) {
                pendientesPago =
                    facturas.filter { factura: Factura -> factura.descEstado == getString(R.string.activitySecond_cardviewFiltroEstado_cbpendientesPago) }
            }

            var listaPorEstado = pagadas + anuladas + planPago + cuotaFija + pendientesPago

            if (listaPorEstado.isEmpty()) {
                listaFiltrada = facturas
            } else {
                listaFiltrada = listaPorEstado
            }

            //Filtrado de facturas por fecha de inicio y fecha de fin
            if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() != getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() != getString(
                    R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
                )
            ) {
                firstDate =
                    formatter.parse(binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString())
                secondDate =
                    formatter.parse(binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString())

                listaFiltrada = listaFiltrada.filter { factura: Factura ->
                    formatoFecha.parse(factura.fecha) >= secondDate && formatoFecha.parse(factura.fecha) <= firstDate
                }
            } else if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() == getString(
                    R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
                ) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() != getString(
                    R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
                )
            ) {
                firstDate =
                    formatter.parse(binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString())
                listaFiltrada =
                    listaFiltrada.filter { factura: Factura -> formatoFecha.parse(factura.fecha) <= firstDate }

            } else if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() != getString(
                    R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
                ) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() == getString(
                    R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
                )
            ) {

                secondDate =
                    formatter.parse(binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString())
                listaFiltrada =
                    listaFiltrada.filter { factura: Factura -> formatoFecha.parse(factura.fecha) >= secondDate }
            } else if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text.toString() == getString(
                    R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
                ) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text.toString() == getString(
                    R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
                )
            )

            //Filtrado de factura por su importe
                if (listaFiltrada.isEmpty()) {
                    listaFiltrada =
                        facturas.filter { factura: Factura -> factura.importeOrdenacion <= binding.activitySecondCardviewFiltroImporteSlImporte.value.toDouble() }
                } else {
                    if (binding.activitySecondCardviewFiltroImporteSlImporte.value != 0.0.toFloat()) {
                        listaFiltrada =
                            listaFiltrada.filter { factura: Factura -> factura.importeOrdenacion <= binding.activitySecondCardviewFiltroImporteSlImporte.value.toDouble() }
                    }
                }
        }

        //Si no se realiza ningunfiltro sobre la lista, devolverá la misma lista cargada al principio
        if (binding.activitySecondCardviewFiltroFechaBtnFechaini.text == getString(R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio) && binding.activitySecondCardviewFiltroFechaBtnFechaFin.text == getString(
                R.string.activitySecond_cardviewFiltroFecha_textoBtnFechaInicio
            ) && binding.activitySecondCardviewFiltroImporteSlImporte.value == 0.0.toFloat() &&
            !binding.activitySecondCardviewFiltroEstadoCbpagadas.isChecked && !binding.activitySecondCardviewFiltroEstadoCbanuladas.isChecked && !binding.activitySecondCardviewFiltroEstadoCbcuotafija.isChecked && !binding.activitySecondCardviewFiltroEstadoCbplanDePago.isChecked && !binding.activitySecondCardviewFiltroEstadoCbpendientesPago.isChecked
        ) {
            listaFiltrada = facturas
        }
        return listaFiltrada
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        if (fecha == fechainicio) {
            calendarfechaDesde.set(year, month, dayOfMonth)
            mostrarFechaFormateada(calendarfechaDesde.timeInMillis)
        } else if (fecha == fechafin) {
            calendarfechHasta.set(year, month, dayOfMonth)
            mostrarFechaFormateadaFin(calendarfechHasta.timeInMillis)
        }
    }

    private fun mostrarFechaFormateada(timestamp: Long) {
        binding.activitySecondCardviewFiltroFechaBtnFechaini.text = formatter.format(timestamp)
        binding.activitySecondCardviewFiltroFechaBtnFechaini.setTextColor(resources.getColor(R.color.black))
    }

    private fun mostrarFechaFormateadaFin(timestamp: Long) {
        binding.activitySecondCardviewFiltroFechaBtnFechaFin.text = formatter.format(timestamp)
        binding.activitySecondCardviewFiltroFechaBtnFechaFin.setTextColor(resources.getColor(R.color.black))
    }
}