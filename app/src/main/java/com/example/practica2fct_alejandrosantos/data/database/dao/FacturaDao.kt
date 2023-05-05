package com.example.practica2fct_alejandrosantos.data.database.dao

import androidx.room.*
import com.example.practica2fct_alejandrosantos.data.model.FacturaModel


@Dao
interface FacturaDao {

    @Query("SELECT * FROM table_facturas")
    suspend fun getFacturas(): List<FacturaModel>

    @Insert
    fun insert(facturas : List<FacturaModel>)

    @Query("Delete  FROM table_facturas")
    suspend fun deleteAllFacturas()
}
