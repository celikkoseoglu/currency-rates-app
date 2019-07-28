package com.cx.currencyrates

import android.app.Application
import android.content.Context

import com.cx.currencyrates.currency.CurrencyModule

class CXApp : Application() {
    private val currencyModule = CurrencyModule()

    companion object {

        fun from(applicationContext: Context): CurrencyModule {
            return (applicationContext as CXApp).currencyModule
        }
    }
}
