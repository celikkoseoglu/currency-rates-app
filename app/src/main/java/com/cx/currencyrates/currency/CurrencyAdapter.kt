package com.cx.currencyrates.currency

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.cx.currencyrates.R
import com.cx.currencyrates.currency.model.Currency
import com.cx.currencyrates.currency.model.Flag
import io.reactivex.subjects.PublishSubject

internal class CurrencyAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currencies = ArrayList<Currency>()

    val mViewClickSubject: PublishSubject<Currency> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.list_item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currency = currencies[position]
        val currencyViewHolder = holder as CurrencyViewHolder
        currencyViewHolder.bind(currency)
    }

    override fun getItemCount() = currencies.size

    fun showCurrencies(currencies: List<Currency>) {
        if (this.currencies.isNotEmpty()) {
            currencies.forEach { newCurrency ->
                this.currencies.first { currency -> currency.name == newCurrency.name }.value = newCurrency.value
            }
        } else {
            this.currencies.clear()
            this.currencies.addAll(currencies)
        }
        notifyDataSetChanged()
    }

    fun moveCurrencyToTop(currency: Currency) {
        val currencyToMoveIndex = currencies.indexOfFirst { currency.name == it.name }
        moveItem(currencyToMoveIndex, 0)
    }

    fun moveItem(from: Int, to: Int) {
        val fromCurrency = currencies[from]
        currencies.removeAt(from)
        if (to < from) {
            currencies.add(to, fromCurrency)
        } else {
            currencies.add(to - 1, fromCurrency)
        }
    }

    inner class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.currency_name)
        lateinit var headline: TextView

        @BindView(R.id.currency_value)
        lateinit var subtitle: TextView

        init {
            view.setOnClickListener {
                mViewClickSubject.onNext(currencies[layoutPosition])
            }
        }

        fun bind(currency: Currency) {
            ButterKnife.bind(this, itemView)
            headline.text = Flag.from(currency.name).value + " " + currency.name
            subtitle.text = currency.value.toString()

            subtitle.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    println("Update numbers!!!")
                }
            })
        }
    }
}
