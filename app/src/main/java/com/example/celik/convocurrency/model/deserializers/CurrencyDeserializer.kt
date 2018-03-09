package com.example.celik.convocurrency.model.deserializers

import com.example.celik.convocurrency.model.AllCurrencies
import com.example.celik.convocurrency.util.AppConstants
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class CurrencyDeserializer : JsonDeserializer<AllCurrencies> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): AllCurrencies {
        val jsonObject = json?.asJsonObject

        val success = jsonObject?.get(AppConstants.SUCCESS).toString()
        val terms = jsonObject?.get(AppConstants.TERMS).toString()
        val privacy = jsonObject?.get(AppConstants.PRIVACY).toString()
        val parameters = readParametersMapFromJson(jsonObject)
        return AllCurrencies(success = AppConstants.SUCCESS == success, terms = terms, privacy = privacy, currencies = parameters)
    }

    private fun readParametersMapFromJson(jsonObject: JsonObject?): Map<String, String> {
        val paramsElement = jsonObject?.get(AppConstants.ALL_CURRENCIES)
        val parametersObject = paramsElement?.asJsonObject
        val parameters = hashMapOf<String, String>()
        for (entry in parametersObject?.entrySet()!!
        ) {
            val key = entry.key
            val value = entry.value.asString
            parameters.apply {
                put(key, value)
            }
        }
        return parameters
    }
}
