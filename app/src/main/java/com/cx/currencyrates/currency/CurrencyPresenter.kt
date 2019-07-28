package com.cx.currencyrates.currency

import com.cx.currencyrates.currency.model.Currency
import com.cx.currencyrates.common.BasePresenter
import com.cx.currencyrates.common.BasePresenterView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class CurrencyPresenter(private val currencyRepository: CurrencyRepository) : BasePresenter<CurrencyPresenter.View>() {

    override fun register(view: View) {
        super.register(view)

        // TODO: error handling
        addToUnsubscribe(view.onRefreshAction()
                .doOnNext { view.showRefreshing(true) }
                .switchMapSingle { currencyRepository.currencyRates().subscribeOn(Schedulers.io()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { currencies ->
                    view.showRefreshing(false)
                    view.showCurrencies(currencies)
                })

        addToUnsubscribe(view.onCurrencyClicked()
                .subscribe {
                    // TODO what do we do on currency click action??
                }
        )
    }

    internal interface View : BasePresenterView {
        fun showRefreshing(isRefreshing: Boolean)

        fun showCurrencies(currencies: List<Currency>)

        fun onCurrencyClicked(): Observable<Currency>

        fun onRefreshAction(): Observable<Long>
    }
}