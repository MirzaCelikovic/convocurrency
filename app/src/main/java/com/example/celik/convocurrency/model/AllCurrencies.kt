package com.example.celik.convocurrency.model

import com.google.gson.annotations.SerializedName

data class AllCurrencies (
    @SerializedName("currencies")
    val results: Map<String, Currency>
)

data class Currency (
        val currencyName : String,
        val currencySymbol : String,
        val id : String
)