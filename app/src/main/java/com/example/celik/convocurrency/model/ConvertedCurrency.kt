package com.example.celik.convocurrency.model

data class ConvertedCurrency (
    val query: Map<String, Int>,
    val results: Map<String, Conversion>
)

data class Conversion(
        val id: String,
        val value: Double,
        val to: String,
        val from: String
)

