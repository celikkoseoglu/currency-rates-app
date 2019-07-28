package com.cx.currencyrates.currency

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class CurrencyPresenterTest {
    @Mock private lateinit var currencyRepository: CurrencyRepository
    @Mock private lateinit var view: CurrencyPresenter.View

    private lateinit var presenter: CurrencyPresenter

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun register() {
        presenter.register(view)

    }
}