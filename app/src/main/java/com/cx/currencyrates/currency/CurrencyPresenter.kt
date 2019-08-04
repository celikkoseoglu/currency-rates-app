package com.cx.currencyrates.currency

import android.os.Handler
import android.os.Looper
import com.cx.currencyrates.common.BasePresenter
import com.cx.currencyrates.common.BasePresenterView
import com.cx.currencyrates.currency.model.Currency
import com.cx.currencyrates.currency.model.CurrencyUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

internal class CurrencyPresenter(private val currencyRepository: CurrencyRepository) : BasePresenter<CurrencyPresenter.View>() {

    private val currencyUtils = CurrencyUtils()

    override fun register(view: View) {
        super.register(view)

        addToUnsubscribe(view.onRefreshAction()
                .doOnNext { Handler(Looper.getMainLooper()).post { view.showRefreshing(true) } }
                .switchMapSingle { currencyRepository.currencyRates().subscribeOn(Schedulers.io()) }
                .retryWhen { t -> t.delay(2, TimeUnit.SECONDS) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { currencyRatios ->
                    currencyUtils.currencyRatios = currencyRatios
                    view.updateCurrencies(currencyRatios)
                    view.showRefreshing(false)
                }
        )

        addToUnsubscribe(view.onCurrencyUpdated()
                .subscribe { updatedCurrency ->
                    view.showCurrencies(currencyUtils.processCurrencies(updatedCurrency))
                }
        )

        addToUnsubscribe(view.onCurrencyClicked()
                .subscribe { clickedCurrency ->
                    view.editCurrencyValue(clickedCurrency)
                }
        )
    }

    internal interface View : BasePresenterView {
        fun showRefreshing(isRefreshing: Boolean)

        fun updateCurrencies(currencies: List<Currency>)

        fun showCurrencies(currencies: List<Currency>)

        fun onCurrencyUpdated(): Observable<Currency>

        fun onCurrencyClicked(): Observable<Currency>

        fun onRefreshAction(): Observable<Long>

        fun editCurrencyValue(currency: Currency)
    }
}