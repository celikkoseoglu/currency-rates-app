package com.cx.currencyrates.currency

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.cx.currencyrates.R
import com.cx.currencyrates.currency.model.Currency
import com.cx.currencyrates.currency.model.CurrencyUtils
import com.cx.currencyrates.currency.model.Flag
import io.reactivex.subjects.PublishSubject

internal class CurrencyAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val currencies = ArrayList<Currency>()

    private val currencyUtils = CurrencyUtils()

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

        currencyUtils.currencyRatios = currencies

        if (this.currencies.isNotEmpty()) {

            //val processedList = currencyUtils.processCurrencies(currencies)


            currencies.forEach { newCurrency ->
                // instead of setting it the value coming from the server, send server value to util class and get the updated value..
                // also, don't update the zeroth value since it is the reference value
                this.currencies.first { currency -> currency.name == newCurrency.name }.value = newCurrency.value
            }
        } else {
            this.currencies.addAll(currencies)
        }
        notifyDataSetChanged()
    }

    fun moveCurrencyToTop(currency: Currency) {
        val currencyToMoveIndex = currencies.indexOfFirst { currency.name == it.name }
        moveItem(currencyToMoveIndex, 0)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        if (from != to) {
            val fromCurrency = currencies[from]
            currencies.removeAt(from)
            if (to < from) {
                currencies.add(to, fromCurrency)
            } else {
                currencies.add(to - 1, fromCurrency)
            }
        }
    }

    inner class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.currency_name)
        lateinit var headline: TextView

        @BindView(R.id.currency_value)
        lateinit var subtitle: EditText

        init {
            view.setOnClickListener {
                mViewClickSubject.onNext(currencies[layoutPosition])
                subtitle.requestFocus()
            }
        }

        fun bind(currency: Currency) {
            ButterKnife.bind(this, itemView)
            headline.text = Flag.from(currency.name).value + " " + currency.name
            subtitle.setText(String.format("%.4f", currency.value))

            subtitle.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (subtitle.hasFocus()) {
                        // text should't be changing for the reference value
                        // update the list with the new value of the EditText
                        currencies[0].value = subtitle.text.toString().toDouble() // this needs error handling, or we could force a number input only
                        println("Update numbers!!!")


                        val processedCurrencies = currencyUtils.processCurrencies(currencies)
                        currencies.clear()
                        currencies.addAll(processedCurrencies)

                        notifyItemRangeChanged(1, currencies.size)

                        // currency utils to be called here as well. Right after an event, update values
                    }
                }
            })

            subtitle.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                            .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                }
            }
        }
    }
}
