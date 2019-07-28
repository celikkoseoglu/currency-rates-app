package com.cx.currencyrates.currency.model

enum class Flag(val value: String) {

    // converted from emoji to unicode via:
    // http://www.russellcottrell.com/greek/utilities/SurrogatePairCalculator.htm

    AUD("\uD83C\uDDE6\uD83C\uDDFA"),
    BGN("\uD83C\uDDE7\uD83C\uDDEC"),
    BRL("\uD83C\uDDE7\uD83C\uDDF7"),
    CAD("\uD83C\uDDE8\uD83C\uDDE6"),
    CHF("\uD83C\uDDE8\uD83C\uDDED"),
    CNY("\uD83C\uDDE8\uD83C\uDDF3"),
    CZK("\uD83C\uDDE8\uD83C\uDDFF"),
    DKK("\uD83C\uDDE9\uD83C\uDDF0"),
    EUR("\uD83C\uDDEA\uD83C\uDDFA"),
    HKD("\uD83C\uDDED\uD83C\uDDF0"),
    HRK("\uD83C\uDDED\uD83C\uDDF7"),
    HUF("\uD83C\uDDED\uD83C\uDDFA"),
    IDR("\uD83C\uDDEE\uD83C\uDDE9"),
    ILS("\uD83C\uDDEE\uD83C\uDDF1"),
    INR("\uD83C\uDDEE\uD83C\uDDF3"),
    ISK("\uD83C\uDDEE\uD83C\uDDF8"),
    JPY("\uD83C\uDDEF\uD83C\uDDF5"),
    KRW("\uD83C\uDDF0\uD83C\uDDF7"),
    MXN("\uD83C\uDDF2\uD83C\uDDFD"),
    MYR("\uD83C\uDDF2\uD83C\uDDFE"),
    NOK("\uD83C\uDDF3\uD83C\uDDF4"),
    NZD("\uD83C\uDDF3\uD83C\uDDFF"),
    PHP("\uD83C\uDDF5\uD83C\uDDED"),
    PLN("\uD83C\uDDF5\uD83C\uDDF1"),
    RON("\uD83C\uDDF7\uD83C\uDDF4"),
    RUB("\uD83C\uDDF7\uD83C\uDDFA"),
    SEK("\uD83C\uDDF8\uD83C\uDDEA"),
    SGD("\uD83C\uDDF8\uD83C\uDDEC"),
    THB("\uD83C\uDDF9\uD83C\uDDED"),
    TRY("\uD83C\uDDF9\uD83C\uDDF7"),
    USD("\uD83C\uDDFA\uD83C\uDDF8"),
    ZAR("\uD83C\uDDFF\uD83C\uDDE6");

    companion object {
        fun from(flagCode: String): Flag = values().first { it.name == flagCode }
    }
}