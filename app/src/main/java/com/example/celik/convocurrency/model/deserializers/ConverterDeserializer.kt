package com.example.celik.convocurrency.model.deserializers

import com.example.celik.convocurrency.model.Conversion
import com.example.celik.convocurrency.model.ConvertedCurrency
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ConverterDeserializer : JsonDeserializer<ConvertedCurrency> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ConvertedCurrency {
        val jsonObject = json?.asJsonObject
        val query = getQueryObject(jsonObject?.get("query").toString())
        val results = getResults(jsonObject?.get("results")?.asJsonObject)
        return ConvertedCurrency(query = query, results = results)
    }

    private fun getQueryObject(jsonObject: String?): Map<String, Int> {
        return Gson().fromJson<Map<String, Int>>(jsonObject.toString())
    }

    private fun getResults(jsonObject: JsonObject?): Map<String, Conversion> {
        val params = hashMapOf<String, Conversion>()
        for (entry in jsonObject?.entrySet()!!) {
            val conversionParams = entry.value.asJsonObject
            val id = conversionParams.get("id").asString
            val value = conversionParams.get("val").asDouble
            val to = conversionParams.get("to").asString
            val from = conversionParams.get("fr").asString
            val conversion = Conversion(id = id, value = value, to = to, from = from)
            params.apply {
                put(entry.key, conversion)
            }
        }
        return params
    }

    private inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
}
