package com.example.celik.convocurrency.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.celik.convocurrency.model.Currency

@Dao
interface AllCurrenciesDao {
    @Query("SELECT * FROM currency")
    fun getAllCurrencies() : List<Currency>

    @Insert
    fun insertAll(vararg currency: Currency)

    @Query("DELETE FROM currency")
    fun deleteAll()
}