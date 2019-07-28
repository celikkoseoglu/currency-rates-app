package com.cx.currencyrates.currency.model

enum class Name(val value: String) {

    AUD("Australian Dollar"),
    BGN("Bulgarian"),
    BRL(""),
    CAD(""),
    CHF(""),
    CNY(""),
    CZK(""),
    DKK(""),
    EUR(""),
    HKD(""),
    HRK(""),
    HUF(""),
    IDR(""),
    ILS(""),
    INR(""),
    ISK(""),
    JPY(""),
    KRW(""),
    MXN(""),
    MYR(""),
    NOK(""),
    NZD(""),
    PHP(""),
    PLN(""),
    RON(""),
    RUB(""),
    SEK(""),
    SGD(""),
    THB(""),
    TRY(""),
    USD(""),
    ZAR("");

    companion object {
        fun from(flagCode: String): Name = values().first { it.name == flagCode }
    }
}