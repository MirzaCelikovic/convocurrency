package com.example.celik.convocurrency.ui

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import com.example.celik.convocurrency.BR
import com.example.celik.convocurrency.api.APIService
import com.example.celik.convocurrency.api.ApiServiceGenerator
import com.example.celik.convocurrency.util.AppConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class AllCurrenciesViewModel : BaseObservable() {

    private var emptyStateVisibility = View.GONE
    private var recyclerViewVisibility = View.GONE
    private lateinit var currencyViewModels : ArrayList<CurrencyViewModel>

    private var allCurrenciesAdapter = MainActivity.AllCurrenciesAdapter()

    fun loadRemoteData() {
        println("Load remote data initiated at: " + Calendar.getInstance())
        ApiServiceGenerator.createService(APIService::class.java).getAllCurrencies(AppConstants.CURRENCY_LAYER_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ allCurrencies ->
                    println("Load remote data finished at: " + Calendar.getInstance())
                    loadLocalData(allCurrencies.currencies)
                }, { throwable ->
                    println("Error: " + throwable.localizedMessage)
                })
    }

    private fun loadLocalData(allCurrencies: Map<String, String>) {
        currencyViewModels = ArrayList()
        allCurrenciesAdapter.clearItems()
        for ((key, value) in allCurrencies) {
            currencyViewModels.add(CurrencyViewModel(key, value))
            allCurrenciesAdapter.addItem(CurrencyViewModel(key, value))
        }

        if (currencyViewModels.isEmpty()) {
            noData()
        } else {
            emptyStateVisibility = View.GONE
            recyclerViewVisibility = View.VISIBLE
            notifyPropertyChanged(BR.emptyStateVisible)
            notifyPropertyChanged(BR.recyclerViewVisible)
        }
    }

    private fun noData() {
        emptyStateVisibility = View.VISIBLE
        recyclerViewVisibility = View.GONE
        notifyPropertyChanged(BR.emptyStateVisible)
        notifyPropertyChanged(BR.recyclerViewVisible)
    }

    @Bindable
    fun getRecyclerViewVisible() : Int {
        return recyclerViewVisibility
    }

    @Bindable
    fun getEmptyStateVisible() : Int {
        return emptyStateVisibility
    }

    @Bindable
    fun getAdapter(): MainActivity.AllCurrenciesAdapter {
        return allCurrenciesAdapter
    }
}