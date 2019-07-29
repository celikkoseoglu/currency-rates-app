package com.cx.currencyrates.currency.model

class CurrencyUtils {

    lateinit var currencyRatios: List<Currency>

    fun processCurrencies(currencyList: List<Currency>): ArrayList<Currency> {

        // if index is 0 then take that value as reference
        // if not then use reference value to update own value??

        val baseCurrency = currencyList[0]
        val baseCurrencyRatio = baseCurrency.value / currencyRatios.first { currency -> currency.name == baseCurrency.name }.value

        // alright multiply all of the currencies except the first one with the ratio and send them back. Might worth a shot?!

        // IT WORKED!!!!!!!!!!! I can probably do the rest tomorrow...

        val newCurrencyList = arrayListOf<Currency>()

        currencyList.forEachIndexed { index, currency ->
            if (index != 0 ) {

                val baseCurrencyValue = currencyRatios.first { baseCurrency -> currency.name == baseCurrency.name  }.value

                newCurrencyList.add(Currency(currency.name, baseCurrencyValue * baseCurrencyRatio))
            } else {
                newCurrencyList.add(Currency(currency.name, currency.value))
            }
        }

        return newCurrencyList

    }
}