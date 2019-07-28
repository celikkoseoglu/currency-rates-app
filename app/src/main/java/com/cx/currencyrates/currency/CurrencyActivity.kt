package com.cx.currencyrates.currency

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import butterknife.BindView
import butterknife.ButterKnife
import com.cx.currencyrates.CXApp
import com.cx.currencyrates.R
import com.cx.currencyrates.currency.model.Currency
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CurrencyActivity : AppCompatActivity(), CurrencyPresenter.View {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.currencies_recyclerview)
    lateinit var recyclerView: RecyclerView

    private lateinit var presenter: CurrencyPresenter
    private lateinit var adapter: CurrencyAdapter

    private val itemTouchHelper by lazy {
        ItemTouchHelper(object : SimpleCallback(UP or DOWN or START or END, 0) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                adapter.moveItem(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })
    }

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

        itemTouchHelper.attachToRecyclerView(recyclerView)
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

    override fun onRefreshAction(): Observable<Long> {

        return Observable.interval(2, TimeUnit.SECONDS)

//        return Observable.create<Any> {
//            recyclerView.announceForAccessibility(getString(R.string.refresh_view_icon_accessibility))
//        }.startWith { }
    }

    override fun showRefreshing(isRefreshing: Boolean) {
        // TODO have a small refreshing indicator somewhere
    }
}
