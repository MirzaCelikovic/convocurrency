package com.example.celik.convocurrency.api

import com.example.celik.convocurrency.model.AllCurrencies
import com.example.celik.convocurrency.model.deserializers.CurrencyDeserializer
import com.example.celik.convocurrency.util.AppConstants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceGenerator {

        fun<S> createService(serviceClass: Class<S>): S {
            val retrofit = Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(createGsonConverter())
                    .client(OkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(serviceClass)
        }

    private fun createGsonConverter(): Converter.Factory {
        val gSonBuilder = GsonBuilder()
        gSonBuilder.registerTypeAdapter(AllCurrencies::class.java, CurrencyDeserializer())
        val gSon = gSonBuilder.create()
        return GsonConverterFactory.create(gSon)
    }
}