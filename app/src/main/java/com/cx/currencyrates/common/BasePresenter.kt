package com.cx.currencyrates.common

import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<in V : BasePresenterView> {
    private val disposables = CompositeDisposable()
    private var view: V? = null

    @CallSuper
    open fun register(view: V) {
        this.view?.let {
            throw IllegalStateException("View $it is already attached. Cannot attach $view")
        }
        this.view = view
    }

    @CallSuper
    fun unregister() {
        view ?: throw IllegalStateException("View is already detached")
        view = null
        disposables.clear()
    }

    @CallSuper
    protected fun addToUnsubscribe(disposable: Disposable) {
        disposables.add(disposable)
    }
}
