package com.example.celik.convocurrency.model

import com.google.gson.annotations.SerializedName

data class AllCurrencies (
    val success: Boolean,
    val terms: String,
    val privacy: String,
    @SerializedName("currencies")
    val currencies: Map<String, String>
)

