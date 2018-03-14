package com.example.celik.convocurrency.util

class AppConstants {

    companion object {
        //without const keyword this class will generate getter (can be seen in decompiled java version)
        const val BASE_URL = "https://free.currencyconverterapi.com/api/"

        const val CURRENCY_NAME = "currencyName"
        const val CURRENCY_SYMBOL = "currencySymbol"
        const val ID = "id"
    }
}

