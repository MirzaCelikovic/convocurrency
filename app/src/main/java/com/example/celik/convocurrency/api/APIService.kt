package com.example.celik.convocurrency.api

import com.example.celik.convocurrency.model.AllCurrencies
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("list")
    fun getAllCurrencies(@Query("access_key") access_key : String): Observable<AllCurrencies>
}