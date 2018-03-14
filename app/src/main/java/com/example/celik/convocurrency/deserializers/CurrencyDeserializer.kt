package com.example.celik.convocurrency.deserializers

import com.example.celik.convocurrency.model.AllCurrencies
import com.example.celik.convocurrency.model.Currency
import com.example.celik.convocurrency.util.AppConstants
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<AllCurrencies> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): AllCurrencies {
        val jsonObject = json?.asJsonObject
        val parameters = readParametersMapFromJson(jsonObject)
        return AllCurrencies(results = parameters)
    }

    private fun readParametersMapFromJson(jsonObject: JsonObject?): List<Currency> {
        val parameters = ArrayList<Currency>()
        val mainObject = jsonObject?.get("results")?.asJsonObject
        for (entry in mainObject?.entrySet()!!) {
            val currencyParams = entry.value.asJsonObject
            val currencyName = currencyParams.get(AppConstants.CURRENCY_NAME).asString
            val currencySymbol = currencyParams.get(AppConstants.CURRENCY_NAME).asString
            val id = currencyParams.get(AppConstants.ID).asString
            val currency = Currency(currencyName = currencyName, currencySymbol = currencySymbol, id = id)
            parameters.apply {
                add(currency)
            }
        }
        return parameters.sortedWith(compareBy { it.currencyName })
    }
}
