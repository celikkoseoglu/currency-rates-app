package com.cx.currencyrates.currency

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.github.tomakehurst.wiremock.junit.WireMockRule

@RunWith(AndroidJUnit4::class)
class CurrencyActivityTest {

    @Rule @JvmField
    val currencyActivityTestRule = ActivityTestRule(CurrencyActivity::class.java)

    @Rule
    var wireMockRule = WireMockRule()

    @Test
    @Throws(Exception::class)
    fun test() {
        currencyActivityTestRule.launchActivity(null)
        //onView(withId(R.id.toolbar)).check(withText(""))
    }
}