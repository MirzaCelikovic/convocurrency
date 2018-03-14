package com.example.celik.convocurrency.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class AllCurrencies (
    @SerializedName("currencies")
    val results: List<Currency>
)

@Entity(indices = [(Index(value = arrayOf("currency_name", "currency_symbol", "currency_abbreviation"), unique = true))])
data class Currency (
        @ColumnInfo(name = "currency_name")
        var currencyName : String,
        @ColumnInfo(name = "currency_symbol")
        var currencySymbol : String,
        @ColumnInfo(name = "currency_abbreviation")
        var id : String
) {
    @PrimaryKey(autoGenerate = false)
    var generatedId : Int? = null
}