package com.example.celik.convocurrency.model

data class LiveCurrencies (
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)

