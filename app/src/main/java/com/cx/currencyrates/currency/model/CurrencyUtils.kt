package com.cx.currencyrates.currency.model

class CurrencyUtils {

    lateinit var currencyRatios: List<Currency>

    fun processCurrencies(updatedCurrency: Currency): ArrayList<Currency> {
        val baseCurrencyRatio = updatedCurrency.value / currencyRatios.first { currency -> currency.name == updatedCurrency.name }.value
        val newCurrencyList = arrayListOf<Currency>()

        currencyRatios.forEach { currency ->
            newCurrencyList.add(Currency(currency.name, currency.value * baseCurrencyRatio))
        }

        return newCurrencyList

    }
}