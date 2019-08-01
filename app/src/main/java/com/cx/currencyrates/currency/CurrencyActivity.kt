package com.cx.currencyrates.currency

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.view.View
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.cx.currencyrates.CXApp
import com.cx.currencyrates.R
import com.cx.currencyrates.currency.model.Currency
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class CurrencyActivity : AppCompatActivity(), CurrencyPresenter.View {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.currencies_recyclerview)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.refresh_indicator)
    lateinit var refreshingIndicator: ProgressBar

    private lateinit var presenter: CurrencyPresenter
    private lateinit var adapter: CurrencyAdapter
    private lateinit var datasetObservable: Observable<Long>

    private val itemTouchHelper by lazy {
        ItemTouchHelper(object : SimpleCallback(UP or DOWN or START or END, 0) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {}
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        datasetObservable = Observable.interval(0, 1, TimeUnit.SECONDS).doOnNext {
            recyclerView.announceForAccessibility(getString(R.string.refreshing_currency_values))
        }

        setContentView(R.layout.activity_currency_list)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        adapter = CurrencyAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as? SimpleItemAnimator
                ?: throw IllegalArgumentException("RecyclerView animator is not of type SimpleItemAnimator"))
                .supportsChangeAnimations = false

        presenter = CXApp.from(applicationContext).inject()
        presenter.register(this)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroy() {
        presenter.unregister()
        super.onDestroy()
    }

    override fun updateCurrencies(currencies: List<Currency>) {
        adapter.updateCurrencies(currencies)
    }

    override fun showCurrencies(currencies: List<Currency>) {
        adapter.showCurrencies(currencies)
    }

    override fun editCurrencyValue(currency: Currency) {
        adapter.moveCurrencyToTop(currency)
    }

    override fun onCurrencyClicked(): Observable<Currency> {
        return adapter.mViewClickSubject
    }

    override fun onRefreshAction(): Observable<Long> {
        return datasetObservable
    }

    override fun showRefreshing(isRefreshing: Boolean) {
        refreshingIndicator.setVisibleOrGone(isRefreshing)
    }
}

fun View.setVisibleOrGone(predicate: Boolean) {
    if (predicate)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

