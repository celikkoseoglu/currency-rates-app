package com.cx.currencyrates.currency

import com.cx.currencyrates.api.CurrencyService
import com.cx.currencyrates.currency.model.Currency
import com.cx.currencyrates.currency.model.CurrencyMapper

import io.reactivex.Single

internal class CurrencyRepository(private val currencyService: CurrencyService, private val currencyMapper: CurrencyMapper) {

    fun currencyRates(): Single<List<Currency>> {
        return currencyService.getExchangeRates("GBP").map { currencyMapper.map(it) }
    }
}
