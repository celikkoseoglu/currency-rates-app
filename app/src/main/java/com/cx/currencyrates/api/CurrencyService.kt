package com.cx.currencyrates.api

import com.cx.currencyrates.api.model.CurrencyResponse

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {
    @GET("latest")
    fun getExchangeRates(@Query("base") currency: String): Single<CurrencyResponse>
}
