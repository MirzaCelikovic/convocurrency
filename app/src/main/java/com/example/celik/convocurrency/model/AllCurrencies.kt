package com.example.celik.convocurrency.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class AllCurrencies (
    @SerializedName("currencies")
    val results: List<Currency>
)

@Entity
data class Currency (
        var currencyName : String,
        var currencySymbol : String,
        var id : String
) {
    @PrimaryKey(autoGenerate = false)
    var generatedId : Int? = null
}