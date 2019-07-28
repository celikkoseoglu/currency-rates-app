package com.cx.currencyrates.api.model

data class CurrencyResponse (
        val base: String,
        val date: String,
        val rates: CurrencyRates
)