package com.cx.currencyrates.currency

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.cx.currencyrates.CXApp
import com.cx.currencyrates.R
import com.cx.currencyrates.currency.model.Currency
import io.reactivex.Observable

class CurrencyActivity : AppCompatActivity(), CurrencyPresenter.View {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.currencies_swiperefreshlayout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.currencies_recyclerview)
    lateinit var recyclerView: RecyclerView

    private lateinit var presenter: CurrencyPresenter
    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_currency_list)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        adapter = CurrencyAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        presenter = CXApp.from(applicationContext).inject()
        presenter.register(this)
    }

    override fun onDestroy() {
        presenter.unregister()
        super.onDestroy()
    }

    override fun showCurrencies(currencies: List<Currency>) {
        adapter.showCurrencies(currencies)
    }

    override fun onCurrencyClicked(): Observable<Currency> {
        return adapter.mViewClickSubject
    }

    override fun onRefreshAction(): Observable<Any> {
        return Observable.create<Any> { emitter ->
            swipeRefreshLayout.setOnRefreshListener {
                emitter.onNext(Observable.just("Hello World"))
                recyclerView.announceForAccessibility(getString(R.string.refresh_view_icon_accessibility))
            }
            emitter.setCancellable { swipeRefreshLayout.setOnRefreshListener(null) }
        }.startWith {  }
    }

    override fun showRefreshing(isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }
}
