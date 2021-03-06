package com.cx.currencyrates.currency

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.*
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
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

    @BindView(R.id.refresh_indicator)
    lateinit var refreshingIndicator: ProgressBar

    private lateinit var presenter: CurrencyPresenter
    private lateinit var adapter: CurrencyAdapter

    private var enableRefreshing = true // is used to disable refresh while user is dragging currencies

    private val itemTouchHelper by lazy {
        ItemTouchHelper(object : SimpleCallback(UP or DOWN or START or END, 0) {

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                enableRefreshing = false
                return super.getMovementFlags(recyclerView, viewHolder)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                adapter.moveItem(from, to)
                return true
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {}

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                enableRefreshing = true
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setContentView(R.layout.activity_currency_list)

        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        adapter = CurrencyAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as? SimpleItemAnimator
                ?: throw IllegalArgumentException("RecyclerView animator is not of type SimpleItemAnimator"))
                .supportsChangeAnimations = false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    recyclerView.clearFocus()
                    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                            .hideSoftInputFromWindow(recyclerView.windowToken, 0)
                }
            }
        })

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
        recyclerView.scrollToPosition(0)
        adapter.moveCurrencyToTop(currency)
    }

    override fun onCurrencyUpdated(): Observable<Currency> {
        return adapter.updateSubject
    }

    override fun onCurrencyClicked(): Observable<Currency> {
        return adapter.clickSubject
    }

    override fun onRefreshAction(): Observable<Long> {
        return Observable.interval(0, 1, TimeUnit.SECONDS).takeWhile { enableRefreshing }.repeat().doOnNext {
            recyclerView.announceForAccessibility(getString(R.string.refreshing_currency_values))
        }
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

