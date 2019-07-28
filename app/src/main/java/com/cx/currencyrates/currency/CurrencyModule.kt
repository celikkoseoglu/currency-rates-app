package com.cx.currencyrates.currency

import com.google.gson.Gson
import com.cx.currencyrates.api.CurrencyService
import com.cx.currencyrates.currency.model.CurrencyMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://revolut.duckdns.org/"

class CurrencyModule {

    internal fun inject() = CurrencyPresenter(CurrencyRepository(createCurrencyService(), CurrencyMapper()))

    private fun createCurrencyService() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CurrencyService::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(getAuthInterceptor())
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    private fun getAuthInterceptor() = Interceptor {
        val original = it.request()
        val hb = original.headers().newBuilder()
        it.proceed(original.newBuilder().headers(hb.build()).build())
    }
}
