package com.example.celik.convocurrency.api

import com.example.celik.convocurrency.model.AllCurrencies
import com.example.celik.convocurrency.model.ConvertedCurrency
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("v5/currencies")
    fun getAllCurrencies(): Observable<AllCurrencies>

    @GET("v5/convert")
    fun convert(@Query("q") q : String) : Observable<ConvertedCurrency>
}