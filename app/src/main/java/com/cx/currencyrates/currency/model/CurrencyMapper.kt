package com.cx.currencyrates.currency.model

import com.cx.currencyrates.api.model.CurrencyResponse
import java.util.*

class CurrencyMapper {

    fun map(currencyResponse: CurrencyResponse): List<Currency> {

        val currencies = ArrayList<Currency>()

        currencies.add(Currency("AUD", currencyResponse.rates.AUD))
        currencies.add(Currency("BGN", currencyResponse.rates.BGN))
        currencies.add(Currency("BRL", currencyResponse.rates.BRL))
        currencies.add(Currency("CAD", currencyResponse.rates.CAD))
        currencies.add(Currency("CHF", currencyResponse.rates.CHF))
        currencies.add(Currency("CNY", currencyResponse.rates.CNY))
        currencies.add(Currency("CZK", currencyResponse.rates.CZK))
        currencies.add(Currency("DKK", currencyResponse.rates.DKK))
        currencies.add(Currency("HKD", currencyResponse.rates.HKD))
        currencies.add(Currency("HRK", currencyResponse.rates.HRK))
        currencies.add(Currency("HUF", currencyResponse.rates.HUF))
        currencies.add(Currency("IDR", currencyResponse.rates.IDR))
        currencies.add(Currency("ILS", currencyResponse.rates.ILS))
        currencies.add(Currency("INR", currencyResponse.rates.INR))
        currencies.add(Currency("ISK", currencyResponse.rates.ISK))
        currencies.add(Currency("JPY", currencyResponse.rates.JPY))
        currencies.add(Currency("KRW", currencyResponse.rates.KRW))
        currencies.add(Currency("MXN", currencyResponse.rates.MXN))
        currencies.add(Currency("MYR", currencyResponse.rates.MYR))
        currencies.add(Currency("NOK", currencyResponse.rates.NOK))
        currencies.add(Currency("NZD", currencyResponse.rates.NZD))
        currencies.add(Currency("PHP", currencyResponse.rates.PHP))
        currencies.add(Currency("PLN", currencyResponse.rates.PLN))
        currencies.add(Currency("RON", currencyResponse.rates.RON))
        currencies.add(Currency("RUB", currencyResponse.rates.RUB))
        currencies.add(Currency("SEK", currencyResponse.rates.SEK))
        currencies.add(Currency("SGD", currencyResponse.rates.SGD))
        currencies.add(Currency("THB", currencyResponse.rates.THB))
        currencies.add(Currency("TRY", currencyResponse.rates.TRY))
        currencies.add(Currency("USD", currencyResponse.rates.USD))
        currencies.add(Currency("ZAR", currencyResponse.rates.ZAR))
        currencies.add(Currency("EUR", currencyResponse.rates.EUR))

        return currencies
    }
}
